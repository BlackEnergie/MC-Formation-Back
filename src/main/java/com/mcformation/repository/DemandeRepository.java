package com.mcformation.repository;

import java.util.List;
import java.util.Optional;
import com.mcformation.model.database.Demande;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DemandeRepository extends CrudRepository<Demande,Long>{
    
    Optional<Demande> findById(Long id);

    Demande findByFormationId(Long id);

    @Query(value="SELECT d.id FROM demande d WHERE d.formation_id =:formationId",nativeQuery =true)
    Long getDemandeIdByFormationId(@Param("formationId") Long formationId);


    @Query(value="SELECT d.demande_id FROM demande_associations_favorables d WHERE d.associations_favorables_utilisateur_id =:idUtilisateur",nativeQuery =true)
    List<Long>   getListDemandeInteressé(@Param("idUtilisateur") Long idUtilisateur);
}
