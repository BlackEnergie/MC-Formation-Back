package com.mcformation.controller;

import com.mcformation.model.api.UtilisateurApi;
import com.mcformation.model.api.UtilisateurDemandeApi;
import com.mcformation.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/utilisateur")
public class UserController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurApi> getUtilisateur(@PathVariable Long id) {
        UtilisateurApi utilisateurApi = utilisateurService.findUtilisateurById(id);
        return new ResponseEntity<>(utilisateurApi, HttpStatus.OK);
    }

    @GetMapping("/demandesFavorables")
    public ResponseEntity<UtilisateurDemandeApi> getUtilisateurDemande(@RequestHeader String Authorization ){
        UtilisateurDemandeApi utilisateurDemandeApi = utilisateurService.findDemandesFavorablesByToken(Authorization);
        return new ResponseEntity<>(utilisateurDemandeApi,HttpStatus.OK);
    }
}
