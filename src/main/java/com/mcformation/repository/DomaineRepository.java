package com.mcformation.repository;

import com.mcformation.model.database.Domaine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DomaineRepository extends CrudRepository<Domaine, Long> {

    Optional<Domaine> findById(Long id);

    Optional<Domaine> findByCode(String code);

    List<Domaine> findByLibelle(String libelle);
}
