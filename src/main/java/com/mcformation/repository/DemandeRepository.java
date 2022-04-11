package com.mcformation.repository;

import java.util.List;
import java.util.Optional;

import com.mcformation.model.database.Demande;

import org.springframework.data.repository.CrudRepository;

public interface DemandeRepository extends CrudRepository<Demande,Long>{
    
    Optional<Demande> findById(Long id);

    List<Demande> findTop5ByOrderByDateDemandeDesc();
}
