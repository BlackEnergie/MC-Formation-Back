package com.mcformation.repository;

import com.mcformation.model.database.auth.CreateUserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface UserTokenRepository extends CrudRepository < CreateUserToken, Long >{

    CreateUserToken findByToken(String token);

    @Query("SELECT c FROM CreateUserToken c WHERE c.expirationDate > CURRENT_TIMESTAMP")
    List<CreateUserToken> findAllByExpirationDateAfterNow();

}
