package com.mcformation.repository;

import com.mcformation.model.database.Cadre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CadreRepository extends CrudRepository<Cadre, Long> {

    List<Cadre> findByLibelle(String libelle);

    Optional<Cadre> findById(Long id);

}