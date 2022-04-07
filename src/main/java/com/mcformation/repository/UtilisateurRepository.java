package com.mcformation.repository;

import com.mcformation.model.database.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {

    Optional<Utilisateur> findById(long id);

    Boolean existsByNomUtilisateur(String nomUtilisateur);

    Optional<Utilisateur> findByNomUtilisateur(String nom);

    Optional<Utilisateur> findByEmail(String email);

}
