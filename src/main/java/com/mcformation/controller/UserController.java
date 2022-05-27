package com.mcformation.controller;

import com.mcformation.model.api.*;
import com.mcformation.model.api.auth.CreateUserTokenApi;
import com.mcformation.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<UtilisateurDemandeApi> getUtilisateurDemandes(@RequestHeader String Authorization) {
        UtilisateurDemandeApi utilisateurDemandeApi = utilisateurService.findDemandesFavorablesByToken(Authorization);
        return new ResponseEntity<>(utilisateurDemandeApi, HttpStatus.OK);
    }

    @GetMapping("/formateur")
    public ResponseEntity<FormateurApi> getFormateurInformation(@RequestHeader String Authorization) {
        FormateurApi formateurApi = utilisateurService.findFormateurInformationsByToken(Authorization);
        return new ResponseEntity<>(formateurApi, HttpStatus.OK);
    }

    @GetMapping("/membresBureauNational")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<List<MembreBureauNationalUserApi>> getMembresBureauNationalInformations() {
        List<MembreBureauNationalUserApi> membreBureauNationalUserApiList = utilisateurService.findAllMembresBureauNationalInfos();
        return new ResponseEntity<>(membreBureauNationalUserApiList, HttpStatus.OK);
    }

    @GetMapping("/formateurs")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<List<FormateurUserApi>> getFormateursInformations() {
        List<FormateurUserApi> formateurUserApi = utilisateurService.findAllFormateursInfos();
        return new ResponseEntity<>(formateurUserApi, HttpStatus.OK);
    }

    @GetMapping("/associations")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<List<AssociationUserApi>> getAssociationsInformations() {
        List<AssociationUserApi> associationUserApi = utilisateurService.findAllAssociationsInfos();
        return new ResponseEntity<>(associationUserApi, HttpStatus.OK);
    }

    @GetMapping("/invitations")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<List<CreateUserTokenApi>> getInvitationsEnAttente() {
        List<CreateUserTokenApi> createUserTokenApiList = utilisateurService.findAllCreateUserTokenInfos();
        return new ResponseEntity<>(createUserTokenApiList, HttpStatus.OK);
    }

    @PostMapping("/modification/actif/{id}")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<MessageApi> postUtilisateurInactif(@PathVariable Long id){
        MessageApi messageApi = utilisateurService.modificationUtilisateurInactif(id);
        return new ResponseEntity<>(messageApi,HttpStatus.OK);
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
