package com.mcformation.service.auth;

import com.mcformation.model.database.Utilisateur;
import com.mcformation.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                .orElseThrow(() -> new UsernameNotFoundException("Aucune utilisateur avec ce nom : " + nomUtilisateur));
        return UserDetailsImpl.build(utilisateur);
    }

}
