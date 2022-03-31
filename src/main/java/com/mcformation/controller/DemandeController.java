package com.mcformation.controller;


import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.database.Demande;
import com.mcformation.service.DemandeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demande")
public class DemandeController {
    @Autowired
    DemandeService demandeService;

    @PostMapping()
    public ResponseEntity<Demande> create(@RequestBody DemandeApi newDemande) {
        Demande demande = demandeService.create(newDemande);
        return new ResponseEntity<>(demande, HttpStatus.OK);
    }
    
}
