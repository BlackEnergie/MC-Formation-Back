package com.mcformation.repository;

import java.util.Optional;

import com.mcformation.model.database.MembreBureauNational;
import org.springframework.data.repository.CrudRepository;

public interface MembreBureauNationalRepository extends CrudRepository<MembreBureauNational, Long> {
    Optional<MembreBureauNational> findById (Long id);
    MembreBureauNational findByPoste (String poste);
}
