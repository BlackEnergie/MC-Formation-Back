package com.mcformation.controller;

import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.auth.FormateurApi;
import com.mcformation.service.DonneesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/data")
public class DonneeController {
    @Autowired
    DonneesService donneesService;

    @GetMapping("/domaines")
    public ResponseEntity<List<DomaineApi>> getAllDomaines() {
        List<DomaineApi> domaines=donneesService.getAllDomaines();
        return new ResponseEntity<>(domaines, HttpStatus.OK);
    }

    @GetMapping("/formateurs")
    public ResponseEntity<List<FormateurApi>> getAllFormateurs() {
        List<FormateurApi> formateurs= donneesService.getAllFormateurs();
        return new ResponseEntity<>(formateurs, HttpStatus.OK);
    }

}
