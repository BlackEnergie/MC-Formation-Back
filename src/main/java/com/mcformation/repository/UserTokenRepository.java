package com.mcformation.repository;

import com.mcformation.model.database.auth.CreateUserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTokenRepository extends CrudRepository < CreateUserToken, Long >{

    CreateUserToken findByToken(String token);

    @Query(value = "SELECT * FROM create_user_token c WHERE c.email NOT IN (SELECT email FROM utilisateur) ORDER BY c.expiration_date DESC", nativeQuery = true)
    List<CreateUserToken> findAllUncompleted();

}
