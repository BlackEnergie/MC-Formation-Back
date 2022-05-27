package com.mcformation.controller;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    DemandeService demandeService;

    @PostMapping("/creer")
    @PreAuthorize("hasRole('ROLE_ASSO') or hasRole('ROLE_BN')")
    public ResponseEntity<MessageApi> create(@Valid @RequestBody DemandeApi demandeApi) {
        MessageApi messageApi = demandeService.create(demandeApi);
        return new ResponseEntity<>(messageApi, HttpStatus.CREATED);
    }

    @PostMapping("/supprimer/{id}")
    @PreAuthorize("hasRole('ROLE_BN')")
    public ResponseEntity<MessageApi> delete(@PathVariable Long id) {
        MessageApi messageApi = demandeService.delete(id);
        return new ResponseEntity<>(messageApi, HttpStatus.CREATED);
    }
}
