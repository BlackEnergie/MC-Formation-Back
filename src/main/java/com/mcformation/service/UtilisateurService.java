package com.mcformation.service;

import com.mcformation.mapper.UtilisateurMapper;
import com.mcformation.model.api.AssociationApi;
import com.mcformation.model.api.UtilisateurApi;
import com.mcformation.model.database.Association;
import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.PasswordTokenRepository;
import com.mcformation.repository.UserTokenRepository;
import com.mcformation.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    private UserTokenRepository userTokenRepository;

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
            switch (utilisateur.getRole().getNom()){
                case ROLE_ASSO:
                    Optional<Association> associationOptional = associationRepository.findById(id);
                    if (associationOptional.isPresent()) {
                        AssociationApi associationApi = UtilisateurMapper.INSTANCE.associationDaoToAssociationApiDetail(associationOptional.get());
                        utilisateurApi.setNomUtilisateur(utilisateur.getNomUtilisateur());
                        utilisateurApi.setEmail(utilisateur.getEmail());
                        utilisateurApi.setAssociationApi(associationApi);

                    }

            }
        } else {
            throw new UnsupportedOperationException("Utilisateur non trouvé");
        }
        return utilisateurApi;
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
