package com.mcformation.repository;

import java.util.Optional;
import com.mcformation.model.database.Demande;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DemandeRepository extends CrudRepository<Demande,Long>, DemandeRepositoryCustom{
    
    Optional<Demande> findById(Long id);

    Demande findByFormationId(Long id);

    @Query(value="SELECT d.id FROM demande d WHERE d.formation_id =:formationId",nativeQuery =true)
    Long getDemandeIdByFormationId(@Param("formationId") Long formationId);
}
