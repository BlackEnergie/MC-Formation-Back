package com.mcformation.repository;

import java.util.Optional;

import com.mcformation.model.database.Formation;

import org.springframework.data.repository.CrudRepository;

public interface FormationRepository extends CrudRepository<Formation,Long>{
    
    Optional<Formation> findById(Long id);
}
