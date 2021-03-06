package com.mcformation.repository;

import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Optional<Utilisateur> findById(Long id);

    Boolean existsByNomUtilisateur(String nomUtilisateur);

    Boolean existsByEmail(String email);

    Optional<Utilisateur> findByNomUtilisateur(String nom);

    Optional<Utilisateur> findByEmail(String email);

}
