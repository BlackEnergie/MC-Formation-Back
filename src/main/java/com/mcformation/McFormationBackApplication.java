package com.mcformation;

import com.mcformation.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McFormationBackApplication {

    @Autowired 
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(McFormationBackApplication.class, args);
    }

}