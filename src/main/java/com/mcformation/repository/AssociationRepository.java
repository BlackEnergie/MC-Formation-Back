package com.mcformation.repository;

import com.mcformation.model.database.Association;
import com.mcformation.model.database.Demande;
import com.mcformation.model.database.Utilisateur;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AssociationRepository extends CrudRepository<Association, Long> {

    Optional<Association> findById(Long id);

    Association findByNomComplet(String nomComplet);


    Association findByDemandes(Demande demande);

    Association findByUtilisateur(Utilisateur utilisateur);
}
