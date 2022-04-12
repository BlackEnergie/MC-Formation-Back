package com.mcformation.service;

import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.PasswordResetToken;
import com.mcformation.repository.PasswordTokenRepository;
import com.mcformation.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

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

}
