package com.mcformation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import com.mcformation.model.api.FormationApi;
import com.mcformation.service.FormationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FormationController {
    @Autowired
    FormationService formationService;

    @GetMapping("/formations")
    public ResponseEntity<List<FormationApi>> getAllFormations() {
        List<FormationApi> formations=formationService.getAllFormations();
        return new ResponseEntity<>(formations, HttpStatus.OK);
        
    }

}
