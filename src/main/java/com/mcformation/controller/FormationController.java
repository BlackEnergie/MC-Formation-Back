package com.mcformation.controller;

import java.util.Date;
import java.util.List;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.FormationApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.database.Domaine;
import com.mcformation.service.FormationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FormationController {

    @Autowired
    FormationService formationService;

    @GetMapping("/formations")
    public ResponseEntity<List<FormationApi>> getAllFormations(@RequestParam int offset,
    @RequestParam int limit,@RequestParam String statut){
        List<FormationApi> formations=formationService.getFormationsAccueil(offset,limit,statut);
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @GetMapping("/formation/{id}")
    public ResponseEntity<FormationApi> getFormation(@PathVariable Long id){
        FormationApi formationApi=formationService.getFormation(id);
        return new ResponseEntity<>(formationApi, HttpStatus.OK);
    }
    @PutMapping("/formation/{id}")
    public ResponseEntity<MessageApi> getFormation(@PathVariable Long id,@RequestBody FormationApi formationApi){
        MessageApi messageApi=formationService.putModification(formationApi);
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

}
