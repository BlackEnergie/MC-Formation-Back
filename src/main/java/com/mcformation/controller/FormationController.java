package com.mcformation.controller;

import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Formateur;
import com.mcformation.repository.DomaineRepository;
import com.mcformation.repository.FormateurRepository;
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
    FormateurRepository formateurRepository;

    @Autowired
    DomaineRepository domaineRepository;

    @GetMapping("/get")
    public ResponseEntity<Domaine> getFormation() {

        Optional<Domaine> domaineOptional = domaineRepository.findById(1L);
        Domaine domaine = domaineOptional.orElse(null);

        return new ResponseEntity<>(domaine, HttpStatus.OK);
    }


}
