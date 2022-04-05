package com.mcformation.controller;

import com.mcformation.model.Erole;
import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.Role;
import com.mcformation.repository.DomaineRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/formation")
public class FormationController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/get")
    public String getFormation() {

        return null;
    }


}
