package com.mcformation.repository;

import java.util.Optional;

import com.mcformation.model.database.Association;
import com.mcformation.model.database.Utilisateur;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssociationRepository extends CrudRepository<Association, Long> {

    Optional<Association> findById (Long id);
  
    Association findByNomComplet(String nomComplet);
    
    @Query(value="SELECT a.* FROM association_demandes ad,association a WHERE a.utilisateur_id=ad.association_utilisateur_id and ad.demandes_id = demandes_id",nativeQuery =true)
    Association findByIdDemande(@Param("demandes_id") Long demandes_id);

    Association findByUtilisateur(Utilisateur utilisateur);
}
