package com.mcformation.controller;

import com.mcformation.model.api.FormateurApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.model.api.UtilisateurApi;
import com.mcformation.model.api.UtilisateurDemandeApi;
import com.mcformation.model.api.UtilisateurChangePasswordApi;
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

    @PutMapping("/modification")
    public ResponseEntity<MessageApi> putUtilisateur(@RequestHeader String Authorization, @RequestBody UtilisateurApi modificationUtilisateur){
        MessageApi messageApi = utilisateurService.modificationUtilisateur(Authorization,modificationUtilisateur);
        return new ResponseEntity<>(messageApi,HttpStatus.OK);
    }

    @PutMapping("/modification/motdepasse")
    public ResponseEntity<MessageApi> putUtilisateurPassword(@RequestHeader String Authorization, @RequestBody UtilisateurChangePasswordApi utilisateurChangePassword){
        MessageApi messageApi = utilisateurService.modificationUtilisateurPassword(Authorization,utilisateurChangePassword);
        return new ResponseEntity<>(messageApi,HttpStatus.OK);
    }

}
