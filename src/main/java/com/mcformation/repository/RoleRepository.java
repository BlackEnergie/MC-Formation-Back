package com.mcformation.repository;

import com.mcformation.model.utils.Erole;
import com.mcformation.model.database.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findById(Long id);
    Optional<Role> findByNom(Erole nom);

}
