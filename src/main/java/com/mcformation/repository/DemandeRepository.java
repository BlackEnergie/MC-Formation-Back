package com.mcformation.repository;

import java.util.List;
import java.util.Optional;

import com.mcformation.model.database.Demande;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DemandeRepository extends CrudRepository<Demande,Long>{
    
    Optional<Demande> findById(Long id);
    
    @Query(value="SELECT demande.* FROM demande WHERE id NOT IN(SELECT demande_id FROM formation)",nativeQuery =true)
    List<Demande> findDemandeNotInFormation();
}
