package com.mcformation.controller;

import com.mcformation.model.api.*;
import com.mcformation.service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FormationController {
    
    @Autowired
    FormationService formationService;

    @GetMapping("/formations")
    public ResponseEntity<List<FormationApi>> getAllFormations() {
        List<FormationApi> formations = formationService.getFormationsAccueil();
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @GetMapping("/formation/{id}")
    @PreAuthorize("hasRole('ROLE_FORMATEUR') or hasRole('ROLE_BN')")
    public ResponseEntity<FormationApi> getFormation(@PathVariable Long id) {
        FormationApi formationApi = formationService.getFormation(id);
        return new ResponseEntity<>(formationApi, HttpStatus.OK);
    }

    @GetMapping("/formation/modal/{id}")
    public ResponseEntity<FormationApi> getFormationLimit(@PathVariable Long id) {
        FormationApi formationApi = formationService.getFormation(id);
        return new ResponseEntity<>(formationApi, HttpStatus.OK);
    }

    @PutMapping("/formation")
    @PreAuthorize("hasRole('ROLE_FORMATEUR') or hasRole('ROLE_BN')")
    public ResponseEntity<MessageApi> putFormation(@RequestBody FormationApi formationApi) {
        MessageApi messageApi = formationService.putModification(formationApi);
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/formation/affectation")
    @PreAuthorize("hasRole('ROLE_FORMATEUR') or hasRole('ROLE_BN')")
    public ResponseEntity<MessageApiDataFormationApi> postAffectationFormation(@RequestBody UtilisateurIdFormationIdApi utilisateurIdFormationIdApi) {
        MessageApiDataFormationApi messageApi = formationService.affecterFormateurFormation(utilisateurIdFormationIdApi.getIdUtilisateur(), utilisateurIdFormationIdApi.getIdFormation());
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

    @PostMapping("/formation/interesser")
    @PreAuthorize("hasRole('ROLE_ASSO')")
    public ResponseEntity<MessageApiDataFormationApi> postAssociationFavorableFormation(@RequestBody UtilisateurIdFormationIdApi utilisateurIdFormationIdApi) {
        MessageApiDataFormationApi messageApi = formationService.interesserFormation(utilisateurIdFormationIdApi.getIdUtilisateur(), utilisateurIdFormationIdApi.getIdFormation());
        return new ResponseEntity<>(messageApi, HttpStatus.OK);
    }

}
