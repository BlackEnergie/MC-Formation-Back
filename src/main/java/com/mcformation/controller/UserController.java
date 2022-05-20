package com.mcformation.controller;

import com.mcformation.model.api.*;
import com.mcformation.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/utilisateur")
public class UserController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("")
    public ResponseEntity<UtilisateurApi> getUtilisateur(@RequestHeader String Authorization) {
        UtilisateurApi utilisateurApi = utilisateurService.findUtilisateurByToken(Authorization);
        return new ResponseEntity<>(utilisateurApi, HttpStatus.OK);
    }

    @GetMapping("/demandesFavorables")
    public ResponseEntity<UtilisateurDemandeApi> getUtilisateurDemande(@RequestHeader String Authorization){
        UtilisateurDemandeApi utilisateurDemandeApi = utilisateurService.findDemandesFavorablesByToken(Authorization);
        return new ResponseEntity<>(utilisateurDemandeApi,HttpStatus.OK);
    }
    @GetMapping("/formateur")
    public ResponseEntity<FormateurApi> getFormateurInformation(@RequestHeader String Authorization){
        FormateurApi formateurApi = utilisateurService.findFormateurInformationsByToken(Authorization);
        return new ResponseEntity<>(formateurApi,HttpStatus.OK);
    }

    @GetMapping("/formateurs")
    public ResponseEntity<List<FormateurUserApi>> getFormateursInformations(){
        List<FormateurUserApi> formateurUserApi = utilisateurService.findAllFormateursInfos();
        return new ResponseEntity<>(formateurUserApi,HttpStatus.OK);
    }

    @GetMapping("/associations")
    public ResponseEntity<List<AssociationUserApi>> getAssociationsInformations(){
        List<AssociationUserApi> associationUserApi = utilisateurService.findAllAssociationsInfos();
        return new ResponseEntity<>(associationUserApi,HttpStatus.OK);
    }

    @PutMapping("/modification")
    public ResponseEntity<MessageApi> putUtilisateur(@RequestHeader String Authorization, @RequestBody UtilisateurApi modificationUtilisateur){
        MessageApi messageApi = utilisateurService.modificationUtilisateur(Authorization,modificationUtilisateur);
        return new ResponseEntity<>(messageApi,HttpStatus.OK);
    }

}
