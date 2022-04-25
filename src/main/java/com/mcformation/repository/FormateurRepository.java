package com.mcformation.repository;

import com.mcformation.model.database.Formateur;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface FormateurRepository extends CrudRepository<Formateur, Long> {

    Formateur findByNom(String nom);

    Optional<Formateur> findByUtilisateurId( Long id);

}
