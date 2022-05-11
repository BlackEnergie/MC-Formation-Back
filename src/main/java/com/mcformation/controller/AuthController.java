package com.mcformation.controller;

import com.mcformation.model.api.MessageApi;
import com.mcformation.model.api.auth.*;
import com.mcformation.model.database.*;
import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.model.utils.Erole;
import com.mcformation.repository.*;
import com.mcformation.service.UtilisateurService;
import com.mcformation.service.auth.UserDetailsImpl;
import com.mcformation.service.email.EmailService;
import com.mcformation.service.email.EmailServiceTemplate;
import com.mcformation.utils.EmailUtils;
import com.mcformation.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @Autowired
    private EmailServiceTemplate emailServiceTemplate;

    ////////////////////////
    //       LOGIN        //
    ////////////////////////


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNomUtilisateur(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByNomUtilisateur(loginRequest.getNomUtilisateur());
        String role = utilisateur.isPresent() ? utilisateur.get().getRole().getNom().toString() : "";

        String jwt = jwtUtils.generateJwtToken(authentication, role);

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
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<MessageApi> inviteUtilisateur(@RequestBody SignupInviteRequest inviteRequest) throws MessagingException {

        CreateUserToken createUserToken = new CreateUserToken();
        createUserToken.setToken(UUID.randomUUID().toString());
        String email = inviteRequest.getEmail();
        if (!EmailUtils.validationEmail(email)) {
            throw new UnsupportedOperationException("Adresse email invalide");
        }
        if (utilisateurRepository.existsByEmail(email)) {
            MessageApi messageApi = new MessageApi(400, "Email existant");
            return new ResponseEntity<>(messageApi, HttpStatus.BAD_REQUEST);
        }
        createUserToken.setEmail(email);
        Erole role = inviteRequest.getRole();
        createUserToken.setRole(role);
        userTokenRepository.save(createUserToken);
        emailServiceTemplate.envoieMailCreationCompte(createUserToken.getEmail(), createUserToken.getToken(), createUserToken.getRole());
        MessageApi messageApi = new MessageApi(200, "Email envoyé");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }


    public void checkToken(String token) {

        String result = utilisateurService.validateEmailToken(token);
        if (result != null) {
            throw new BadCredentialsException(result);
        }
    }

    @PostMapping("/signup/checkToken")
    public ResponseEntity<MessageApi> checkEmailToken(@RequestParam("token") String token) {

        checkToken(token);
        Erole role = userTokenRepository.findByToken(token).getRole();

        MessageApi messageApi = new MessageApi(200, role.name());
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }


    @PostMapping("/signup/create")
    public ResponseEntity<?> creationUtilisateur(@Valid @RequestBody SignupRequest signUpRequest, @RequestParam String token) throws MessagingException {

        checkToken(token);

        if (Boolean.TRUE.equals(utilisateurRepository.existsByNomUtilisateur(signUpRequest.getNomUtilisateur()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Le nom d'utilisateur est déjà pris"));
        }
        String email = userTokenRepository.findByToken(token).getEmail();
        if (Boolean.TRUE.equals(utilisateurRepository.existsByEmail(email))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Il existe déjà un utilisateur avec cette adresse email"));
        }

        // Create new user's account
        Utilisateur utilisateur = new Utilisateur(signUpRequest.getNomUtilisateur(), email, encoder.encode(signUpRequest.getPassword()));
        Erole erole = userTokenRepository.findByToken(token).getRole();
        Role role = new Role();
        role.setNom(erole);
        Association association = signUpRequest.getAssociation();
        Formateur formateur = signUpRequest.getFormateur();
        MembreBureauNational membreBureauNational = signUpRequest.getMembreBureauNational();

        if (erole == Erole.ROLE_ASSO && association != null) {
            utilisateur.setRole(role);
            utilisateur = saveUtilisateur(utilisateur, erole);
            association.setUtilisateur(utilisateur);
            associationRepository.save(association);

        } else if (erole == Erole.ROLE_FORMATEUR && formateur != null) {
            utilisateur.setRole(role);
            utilisateur = saveUtilisateur(utilisateur, erole);
            formateur.setUtilisateur(utilisateur);
            formateurRepository.save(formateur);

        } else if (erole == Erole.ROLE_BN && membreBureauNational != null) {
            utilisateur.setRole(role);
            utilisateur = saveUtilisateur(utilisateur, erole);
            membreBureauNational.setUtilisateur(utilisateur);
            membreBureauNationalRepository.save(membreBureauNational);
        } else {
            throw new UnsupportedOperationException("Requête invalide");
        }

        emailServiceTemplate.confirmationCreationCompte(utilisateur.getEmail(), utilisateur.getNomUtilisateur());
        CreateUserToken createUserToken = userTokenRepository.findByToken(token);
        createUserToken.setExpirationDate(new Timestamp(System.currentTimeMillis()));
        userTokenRepository.save(createUserToken);
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès"));
    }


    @PostMapping("/signup/admin")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<?> registerUserAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
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
            throw new UnsupportedOperationException("Requête invalide");

        }
        emailService.sendNewUserNotification(utilisateur.getEmail(), utilisateur.getNomUtilisateur(), utilisateur.getRole());

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès"));
    }

    private Utilisateur saveUtilisateur(Utilisateur utilisateur, Erole erole) {
        Role role = roleRepository.findByNom(erole).orElseThrow(() -> new UnsupportedOperationException("Le role n'existe pas"));
        utilisateur.setRole(role);
        return utilisateurRepository.save(utilisateur);
    }


    ////////////////////////
    //   RESET PASSWORD   //
    ////////////////////////

    @PostMapping("/resetPassword/invite")
    public ResponseEntity<MessageApi> resetPassword(@RequestParam("email") String userEmail) throws MessagingException {

        Utilisateur utilisateur = utilisateurService.findUtilisateurByEmail(userEmail);
        ArrayList<PasswordResetToken> passwordResetToken = (ArrayList<PasswordResetToken>) passwordTokenRepository.findAllByUtilisateur_Id(utilisateur.getId());
        ArrayList<String> result = new ArrayList<>();

        for (PasswordResetToken listToken : passwordResetToken) {
            result.add(utilisateurService.validatePasswordResetToken(listToken.getToken()));

        }
        if (!result.contains("Token valide")) {
            String token = UUID.randomUUID().toString();
            utilisateurService.createPasswordResetTokenForUtilisateur(utilisateur, token);
            emailServiceTemplate.envoieResetPassowrd(token, utilisateur);
        } else {
            String resendToken = passwordResetToken.get(result.indexOf("Token valide")).getToken();
            emailServiceTemplate.envoieResetPassowrd(resendToken, utilisateur);
        }
        ;
        MessageApi messageApi = new MessageApi(200, "Votre demande a été prise en compte, vous allez recevoir un email si votre compte existe.");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/resetPassword/checkToken")
    public ResponseEntity<MessageApi> checkPasswordTokenValid(@RequestParam("token") String token) {
        String result = utilisateurService.validatePasswordResetToken(token);
        if (result != "Token valide") {
            throw new BadCredentialsException(result);
        }
        MessageApi messageApi = new MessageApi(200, "Token valide");
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/resetPassword/save")
    public ResponseEntity<MessageApi> savePassword(@RequestBody PasswordApi passwordApi) {
        String result = utilisateurService.validatePasswordResetToken(passwordApi.getToken());
        if (result != "Token valide") {
            throw new BadCredentialsException(result);
        }
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(passwordApi.getToken());
        Utilisateur utilisateur = (Utilisateur) passwordResetToken.getUtilisateur();
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
