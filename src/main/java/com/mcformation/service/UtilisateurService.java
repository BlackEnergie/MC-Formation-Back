package com.mcformation.service;

import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.*;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.MembreBureauNational;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.repository.*;
import com.mcformation.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur findUtilisateurByEmail(String email){
        Utilisateur utilisateur;
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
        } else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return utilisateur;
    }

    public UtilisateurApi findUtilisateurById(Long id){
        Utilisateur utilisateur;
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);
        UtilisateurApi utilisateurApi = new UtilisateurApi();
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
            utilisateurApi.setNomUtilisateur(utilisateur.getNomUtilisateur());
            utilisateurApi.setEmail(utilisateur.getEmail());
            switch (utilisateur.getRole().getNom()){
                case ROLE_ASSO:
                    Optional<Association> associationOptional = associationRepository.findById(id);
                    if (associationOptional.isPresent()) {
                        AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiDetail(associationOptional.get());
                        utilisateurApi.setAssociationApi(associationApi);
                    }
                case ROLE_FORMATEUR:
                    Optional<Formateur> formateurOptional = formateurRepository.findById(id);
                    if (formateurOptional.isPresent()){
                        FormateurApi formationApi = UtilisateurMapper.INSTANCE.formateurDaoToFormateurApiDetail(formateurOptional.get());
                        utilisateurApi.setFormateurApi(formationApi);
                    }
                case ROLE_BN:
                    Optional<MembreBureauNational> membreBureauNationalOptional = membreBureauNationalRepository.findById(id);
                    if (membreBureauNationalOptional.isPresent()){
                        MembreBureauNationalApi membreBureauNationalApi = UtilisateurMapper.INSTANCE.membreBureauNationalDaoTomembreBureauNationalApiDetail(membreBureauNationalOptional.get());
                        utilisateurApi.setMembreBureauNationalApi(membreBureauNationalApi);
                    }
            }
        } else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return utilisateurApi;
    }

    public UtilisateurDemandeApi findDemandesFavorablesByToken(String authorization) {
        String token = authorization.substring(7, authorization.length());
        Long idUtilisateur =jwtUtils.getIdFromJwtToken(token);
        UtilisateurDemandeApi utilisateurDemandeApi = new UtilisateurDemandeApi();
        Optional<Association> association = associationRepository.findById(idUtilisateur);
        if(association.isPresent()) {
            List<Long> demandesFavorables = demandeRepository.getListDemandeInteressé(idUtilisateur);
            utilisateurDemandeApi.setAcronyme(association.get().getAcronyme());
            utilisateurDemandeApi.setDemandesFavorables(demandesFavorables);
        }
        else{
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return  utilisateurDemandeApi;
    }
    //PASSWORD

    public void createPasswordResetTokenForUtilisateur(Utilisateur utilisateur, String token) {
        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setUtilisateur(utilisateur);
        myToken.setToken(token);
        passwordTokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);


        return !isTokenFound(passToken) ? "Token invalide"
                : isTokenExpired(passToken) ? "Token expiré"
                : !isTokenExpired(passToken) ? "Token valide"
                :null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpirationDate().before(cal.getTime());
    }

    public void changeUserPassword(Utilisateur utilisateur, String password) {
        utilisateur.setPassword(passwordEncoder.encode(password));
        utilisateurRepository.save(utilisateur);
    }

    public Utilisateur getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
        return passwordResetToken.getUtilisateur();
    }

    //EMAIL

    public String validateEmailToken(String token) {
        final CreateUserToken passToken = userTokenRepository.findByToken(token);
        return !isUserTokenFound(passToken) ? "Token invalide"
                : isUserTokenExpired(passToken) ? "Token expiré"
                : null;
    }

    public String getRoleByToken(String token){
        final CreateUserToken passToken = userTokenRepository.findByToken(token);
        return passToken.getRole().toString();
    }

    private boolean isUserTokenFound(CreateUserToken passToken) {
        return passToken != null;
    }

    private boolean isUserTokenExpired(CreateUserToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpirationDate().before(cal.getTime());
    }


}
