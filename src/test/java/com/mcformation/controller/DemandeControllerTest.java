package com.mcformation.controller;

import com.mcformation.model.api.DemandeApi;
import com.mcformation.model.api.DomaineApi;
import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.repository.*;
import com.mcformation.utils.JsonUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/application-test.properties")
class DemandeControllerTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private FormationRepository formationRepository;

    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final String password = "7d750e46b0be4ed46b1c8fa44fd0221f732d2e734cc431cc78d4073e9bec95de";

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanDatabase() {
        associationRepository.deleteAll();
        formateurRepository.deleteAll();
        membreBureauNationalRepository.deleteAll();
        formationRepository.deleteAll();
        demandeRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    public void creerDemande() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        DomaineApi domaineApi = new DomaineApi();
        domaineApi.setCode("1");
        domaineApi.setLibelle("Administration associatif");
        domaineApi.setDescription("Comment gérer votre association administrativement.");
        List<DomaineApi> listDomaines = new ArrayList<>();
        listDomaines.add(domaineApi);
        DemandeApi demandeApi = new DemandeApi();
        demandeApi.setSujet("TestSujet");
        demandeApi.setDetail("TestDetail");
        demandeApi.setDomaines(listDomaines);
        demandeApi.setNomUtilisateur("asso");
        String request = JsonUtils.objectToJson(demandeApi);
        this.mvc.perform(post("/demande/creer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(status().isCreated());
    }

    private String getLoginAccessToken(String nomUtilisateur, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur(nomUtilisateur);
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        MvcResult result = this.mvc.perform(post("/auth/signin")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        return json.getString("accessToken");
    }
}
