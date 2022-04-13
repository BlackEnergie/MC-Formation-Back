package com.mcformation.controller;

import com.mcformation.model.utils.Erole;
import com.mcformation.model.database.*;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.MembreBureauNationalRepository;
import com.mcformation.repository.RoleRepository;
import com.mcformation.repository.UtilisateurRepository;
import com.mcformation.utils.JwtUtils;
import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.model.api.auth.SignupRequest;
import com.mcformation.model.api.auth.JwtResponse;
import com.mcformation.model.api.auth.MessageResponse;
import com.mcformation.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AssociationRepository associationRepository;
    @Autowired
    FormateurRepository formateurRepository;
    @Autowired
    MembreBureauNationalRepository membreBureauNationalRepository;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNomUtilisateur(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNomUtilisateur(loginRequest.getNomUtilisateur());
        String role = utilisateur.isPresent()? utilisateur.get().getRoles().stream().findFirst().get().getNom().toString() : "";
        String jwt = jwtUtils.generateJwtToken(authentication, role);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getNomUtilisateur(),
                userDetails.getEmail(),
                roles));
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (utilisateurRepository.existsByNomUtilisateur(signUpRequest.getNomUtilisateur())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // Create new user's account
        Utilisateur utilisateur = new Utilisateur(signUpRequest.getNomUtilisateur(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));


        Set<Role> roles = new HashSet<>();
        signUpRequest.setRole(null);

        Association association = signUpRequest.getAssociation();
        MembreBureauNational membreBureauNational = signUpRequest.getMembreBureauNational();
        Formateur formateur = signUpRequest.getFormateur();

        boolean requestValid = false;


        if (association != null) {
            if (membreBureauNational == null && formateur == null) {
                utilisateur = saveUtilisateur(utilisateur, Erole.ROLE_ASSO);
                association.setUtilisateur(utilisateur);
                associationRepository.save(association);
                requestValid = true;
            }
        }

        if (membreBureauNational != null) {
            if (association == null && formateur == null) {

                utilisateur = saveUtilisateur(utilisateur, Erole.ROLE_BN);
                membreBureauNational.setUtilisateur(utilisateur);
                membreBureauNationalRepository.save(membreBureauNational);
                requestValid = true;
            }
        }

        if (formateur != null) {
            if (association == null && membreBureauNational == null) {
                utilisateur = saveUtilisateur(utilisateur, Erole.ROLE_FORMATEUR);
                formateur.setUtilisateur(utilisateur);
                formateurRepository.save(formateur);
                requestValid = true;
            }
        }
        if (!requestValid) {
            throw new RuntimeException("Erreur : requête invalide");

        }

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }

    private Utilisateur saveUtilisateur(Utilisateur utilisateur, Erole erole) {
        Role role = roleRepository.findByNom(erole).orElseThrow(() -> new RuntimeException("Erreur: Le role n'existe pas"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        utilisateur.setRoles(roles);
        return utilisateurRepository.save(utilisateur);
    }

}
