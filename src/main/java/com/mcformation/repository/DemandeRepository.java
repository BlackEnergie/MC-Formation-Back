package com.mcformation.repository;

import java.util.Optional;

import com.mcformation.model.database.Demande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DemandeRepository extends PagingAndSortingRepository<Demande,Long>{
    
    Optional<Demande> findById(Long id);

    Page<Demande> findAll(Pageable pageable);
}
