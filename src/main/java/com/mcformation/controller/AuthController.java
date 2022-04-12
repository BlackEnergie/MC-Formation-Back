package com.mcformation.controller;

import com.mcformation.model.api.MessageApi;
import com.mcformation.model.utils.Erole;
import com.mcformation.model.database.*;
import com.mcformation.repository.*;
import com.mcformation.security.jwt.JwtUtils;
import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.model.api.auth.SignupRequest;
import com.mcformation.model.api.auth.JwtResponse;
import com.mcformation.model.api.auth.MessageResponse;
import com.mcformation.service.UtilisateurService;
import com.mcformation.service.auth.UserDetailsImpl;
import com.mcformation.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AssociationRepository associationRepository;
    @Autowired
    private FormateurRepository formateurRepository;
    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNomUtilisateur(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

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
        Erole role = null;
        signUpRequest.setRole(null);

        Association association = signUpRequest.getAssociation();
        MembreBureauNational membreBureauNational = signUpRequest.getMembreBureauNational();
        Formateur formateur = signUpRequest.getFormateur();

        boolean requestValid = false;


        if (association != null) {
            if (membreBureauNational == null && formateur == null) {
                role = Erole.ROLE_ASSO;
                utilisateur = saveUtilisateur(utilisateur, role);
                association.setUtilisateur(utilisateur);
                associationRepository.save(association);
                requestValid = true;
            }
        }

        if (membreBureauNational != null) {
            if (association == null && formateur == null) {
                role = Erole.ROLE_BN;
                utilisateur = saveUtilisateur(utilisateur, role);
                membreBureauNational.setUtilisateur(utilisateur);
                membreBureauNationalRepository.save(membreBureauNational);
                requestValid = true;
            }
        }

        if (formateur != null) {
            if (association == null && membreBureauNational == null) {
                role = Erole.ROLE_FORMATEUR;
                utilisateur = saveUtilisateur(utilisateur, role);
                formateur.setUtilisateur(utilisateur);
                formateurRepository.save(formateur);
                requestValid = true;
            }
        }
        if (!requestValid) {
            throw new RuntimeException("Erreur : requête invalide");

        }
        emailService.sendNewUserNotification(utilisateur.getEmail(), utilisateur.getNomUtilisateur(), role);
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<MessageApi> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        utilisateurService.createPasswordResetTokenForUtilisateur(utilisateur, token);
        emailService.sendResetTokenEmail(token, utilisateur);
        MessageApi messageApi = new MessageApi(200, "Email envoyé");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    private Utilisateur saveUtilisateur(Utilisateur utilisateur, Erole erole) {
        Role role = roleRepository.findByNom(erole).orElseThrow(() -> new RuntimeException("Erreur: Le role n'existe pas"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        utilisateur.setRoles(roles);
        return utilisateurRepository.save(utilisateur);
    }

}
