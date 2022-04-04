package com.mcformation.controller;

import com.mcformation.model.Erole;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Role;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.MembreBureauNationalRepository;
import com.mcformation.repository.RoleRepository;
import com.mcformation.repository.UtilisateurRepository;
import com.mcformation.security.jwt.JwtUtils;
import com.mcformation.security.jwt.payload.request.LoginRequest;
import com.mcformation.security.jwt.payload.request.SignupRequest;
import com.mcformation.security.jwt.payload.response.JwtResponse;
import com.mcformation.security.jwt.payload.response.MessageResponse;
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
        utilisateur.setAssociation(associationRepository.findByNomComplet(signUpRequest.getAssociation()));
        utilisateur.setFormateur(formateurRepository.findByNom(signUpRequest.getFormateur()));
        utilisateur.setMembreBureauNational(membreBureauNationalRepository.findByPoste(signUpRequest.getMembreBureauNational()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByNom(Erole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByNom(Erole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByNom(Erole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        utilisateur.setRoles(roles);
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
