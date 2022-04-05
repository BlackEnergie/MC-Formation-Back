package com.mcformation.controller;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.MessageApi;
import com.mcformation.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/demande")
public class DemandeController {

    @Autowired
    DemandeService demandeService;

    @PostMapping("/creer")
    public ResponseEntity<MessageApi> create(@Valid @RequestBody DemandeApi demandeApi) {
        MessageApi messageApi = demandeService.create(demandeApi);
        return new ResponseEntity<>(messageApi, HttpStatus.CREATED);
    }

}
