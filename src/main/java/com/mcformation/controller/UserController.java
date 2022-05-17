package com.mcformation.controller;

import com.mcformation.model.api.UtilisateurApi;
import com.mcformation.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    UtilisateurService utilisateurService;

    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<UtilisateurApi> getUtilisateur(@PathVariable Long id) {
        UtilisateurApi utilisateurApi = utilisateurService.findUtilisateurById(id);
        return new ResponseEntity<>(utilisateurApi, HttpStatus.OK);
    }
}
