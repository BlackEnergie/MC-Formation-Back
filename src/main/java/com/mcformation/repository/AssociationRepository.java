package com.mcformation.repository;

import java.util.List;
import java.util.Optional;

import com.mcformation.model.database.Association;
import org.springframework.data.repository.CrudRepository;

public interface AssociationRepository extends CrudRepository<Association, Long> {

    Optional<Association> findById (Long id);
  
    Association findByNomComplet(String nomComplet);
  
    Optional<Association> findByEmail(String email);

}
