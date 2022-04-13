package com.mcformation.controller;

import com.mcformation.model.api.MessageApi;
import com.mcformation.model.api.auth.*;
import com.mcformation.model.database.*;
import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.model.utils.Erole;
import com.mcformation.repository.*;
import com.mcformation.security.jwt.JwtUtils;
import com.mcformation.service.UtilisateurService;
import com.mcformation.service.auth.UserDetailsImpl;
import com.mcformation.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
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
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;

    ////////////////////////
    //       LOGIN        //
    ////////////////////////

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNomUtilisateur(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getNomUtilisateur(), userDetails.getEmail(), roles));
    }

    ////////////////////////
    //      REGISTER      //
    ////////////////////////

    @PostMapping("/signup/invite")
    public ResponseEntity<MessageApi> inviteUtilisateur(@RequestBody SignupInviteRequest inviteRequest) {

        CreateUserToken createUserToken = new CreateUserToken();
        createUserToken.setToken(UUID.randomUUID().toString());
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(inviteRequest.getEmail());
        Erole role = null;
        if (inviteRequest.getRole().equals("Association")) {
            role = Erole.ROLE_ASSO;
            createUserToken.setRole(role);
            utilisateur = saveUtilisateur(utilisateur, role);
            createUserToken.setUtilisateur(utilisateur);
            userTokenRepository.save(createUserToken);
            utilisateurRepository.save(utilisateur);

        }
        if (inviteRequest.getRole().equals("Formateur")) {
            role = Erole.ROLE_FORMATEUR;
            createUserToken.setRole(role);
            utilisateur = saveUtilisateur(utilisateur, role);
            createUserToken.setUtilisateur(utilisateur);
            userTokenRepository.save(createUserToken);
            utilisateurRepository.save(utilisateur);
        }
        emailService.sendCreateUserTokenEmail(createUserToken.getToken(), utilisateur);
        MessageApi messageApi = new MessageApi(200, "Email envoyé");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }


    public void checkToken(String token) {

        String result = utilisateurService.validateEmailToken(token);
        if (result != null) {
            throw new BadCredentialsException(result);
        }
    }

    @PostMapping("/emailToken/checkToken")
    public ResponseEntity<MessageApi> checkEmailToken(@RequestParam("token") String token) {

        checkToken(token);
        String role = utilisateurService.getRoleByToken(token);

        MessageApi messageApi = new MessageApi(200, role);
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }


    @PostMapping("/signupEmail")
    public ResponseEntity<?> creationUtilisateur(@Valid @RequestBody SignupRequest signUpRequest, @RequestParam String token) {

        checkToken(token);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(userTokenRepository.findByToken(token).getUtilisateur().getEmail());

        if (utilisateurOptional.isPresent()) {
            if (utilisateurRepository.existsByNomUtilisateur(signUpRequest.getNomUtilisateur())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }
            // Create new user's account

            Utilisateur utilisateur = utilisateurOptional.get();
            utilisateur.setNomUtilisateur(signUpRequest.getNomUtilisateur());
            utilisateur.setPassword(encoder.encode(signUpRequest.getPassword()));



            Association association = signUpRequest.getAssociation();
            Formateur formateur = signUpRequest.getFormateur();

            boolean requestValid = false;

            if (utilisateur.getRoles().stream().findFirst().get().getNom().equals(Erole.ROLE_ASSO)) {
                    association.setUtilisateur(utilisateur);
                    associationRepository.save(association);
                    requestValid = true;
            }

            if (utilisateur.getRoles().stream().findFirst().get().getNom().equals(Erole.ROLE_FORMATEUR)) {
                    formateur.setUtilisateur(utilisateur);
                    formateurRepository.save(formateur);
                    requestValid = true;
            }
            if (!requestValid) {
                throw new RuntimeException("Erreur : requête invalide");

            }
            emailService.sendNewUserNotification(utilisateur.getEmail(), utilisateur.getNomUtilisateur(), role);
            CreateUserToken createUserToken = userTokenRepository.findByToken(token);
            createUserToken.setExpirationDate(new Timestamp(System.currentTimeMillis()));
            userTokenRepository.save(createUserToken);
            return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès"));
        }
        throw new BadCredentialsException("Mauvaises informations saisies");


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
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès"));
    }

    private Utilisateur saveUtilisateur(Utilisateur utilisateur, Erole erole) {
        Role role = roleRepository.findByNom(erole).orElseThrow(() -> new RuntimeException("Erreur: Le role n'existe pas"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        utilisateur.setRoles(roles);
        return utilisateurRepository.save(utilisateur);
    }

    ////////////////////////
    //   RESET PASSWORD   //
    ////////////////////////

    @PostMapping("/resetPassword")
    public ResponseEntity<MessageApi> resetPassword(@RequestParam("email") String userEmail) {
        Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(userEmail);
        String token = UUID.randomUUID().toString();
        utilisateurService.createPasswordResetTokenForUtilisateur(utilisateur, token);
        emailService.sendResetTokenEmail(token, utilisateur);
        MessageApi messageApi = new MessageApi(200, "Email envoyé");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/resetPassword/checkToken")
    public ResponseEntity<MessageApi> checkPasswordTokenValid(@RequestParam("token") String token) {
        String result = utilisateurService.validatePasswordResetToken(token);
        if (result != null) {
            throw new BadCredentialsException(result);
        }
        MessageApi messageApi = new MessageApi(200, "Token valide");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/resetPassword/savePassword")
    public ResponseEntity<MessageApi> savePassword(@RequestBody PasswordApi passwordApi) {
        String result = utilisateurService.validatePasswordResetToken(passwordApi.getToken());
        if (result != null) {
            throw new BadCredentialsException(result);
        }
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(passwordApi.getToken());
        Utilisateur utilisateur = passwordResetToken.getUtilisateur();
        if (utilisateur != null) {
            utilisateurService.changeUserPassword(utilisateur, passwordApi.getNewPassword());
        } else {
            throw new BadCredentialsException("Pas d'utilisateur associé");
        }
        passwordResetToken.setExpirationDate(new Timestamp(System.currentTimeMillis()));
        passwordTokenRepository.save(passwordResetToken);
        MessageApi messageApi = new MessageApi(200, "Mot de passe modifié avec succès");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

}
