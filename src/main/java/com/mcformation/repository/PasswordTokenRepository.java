package com.mcformation.repository;


import com.mcformation.model.database.Utilisateur;
import com.mcformation.model.database.auth.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findById(Long id);
    Iterable<PasswordResetToken> findAllByUtilisateur_Id(Long id);


    PasswordResetToken findByToken(String token);


}
