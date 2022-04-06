package com.mcformation.repository;

import java.util.Optional;

import com.mcformation.model.database.Association;
import org.springframework.data.repository.CrudRepository;

public interface AssociationRepository extends CrudRepository<Association, Long> {

    Optional<Association> findByEmail(String email);
}
