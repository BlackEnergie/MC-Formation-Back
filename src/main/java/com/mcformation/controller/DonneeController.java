package com.mcformation.controller;

import java.util.List;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.service.DonneesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
public class DonneeController {
    @Autowired
    DonneesService donneesService;

    @GetMapping("/domaines")
    public ResponseEntity<List> getAllDomaines() {
        List<DomaineApi> domaines=donneesService.getAllDomaines();
        return new ResponseEntity<>(domaines, HttpStatus.OK);
    }


}
