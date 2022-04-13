package com.mcformation.repository;

import com.mcformation.model.database.auth.CreateUserToken;
import org.springframework.data.repository.CrudRepository;

public interface UserTokenRepository extends CrudRepository < CreateUserToken, Long >{

    CreateUserToken findByToken(String token);


}
