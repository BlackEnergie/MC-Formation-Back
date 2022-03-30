package com.mcformation.repository;

import com.mcformation.model.database.Ressource;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RessourceRepository extends CrudRepository<Ressource, Long> {

        List<Ressource> findByLibelle(String libelle);

        Optional<Ressource> findById(Long id);

    }

