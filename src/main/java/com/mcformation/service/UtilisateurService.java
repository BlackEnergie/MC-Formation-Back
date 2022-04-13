package com.mcformation.service;

import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.repository.PasswordTokenRepository;
import com.mcformation.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private PasswordEncoder passwordEncoder;

    public Utilisateur findUtilisateurByEmail(String email) {
        Utilisateur utilisateur;
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            utilisateur = utilisateurOptional.get();
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        return utilisateur;
    }

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
                : null;
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
}
