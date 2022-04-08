package com.mcformation.repository;

import com.mcformation.model.database.Formateur;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FormateurRepository extends CrudRepository<Formateur, Long> {

    Formateur findByNom(String nom);

    Optional<Formateur> findById(Long id);
    
    @Query(value="SELECT f.* FROM formation_formateurs ff,formateur f WHERE ff.formateurs_utilisateur_id=f.utilisateur_id and ff.formation_id = formation_id",nativeQuery =true)
    List<Formateur> findByIdFormation(@Param("formation_id") Long formation_id);

}
