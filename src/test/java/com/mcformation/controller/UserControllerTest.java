package com.mcformation.controller;

import com.mcformation.model.api.*;
import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.model.utils.College;
import com.mcformation.repository.AssociationRepository;
import com.mcformation.repository.FormateurRepository;
import com.mcformation.repository.MembreBureauNationalRepository;
import com.mcformation.repository.UtilisateurRepository;
import com.mcformation.utils.JsonUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/application-test.properties")

class UserControllerTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    private final String password = "7d750e46b0be4ed46b1c8fa44fd0221f732d2e734cc431cc78d4073e9bec95de";

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @AfterEach
    public void cleanDatabase() {
        associationRepository.deleteAll();
        formateurRepository.deleteAll();
        membreBureauNationalRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getUtilisateurAssociation_Success() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        this.mvc.perform(
                        get("/utilisateur")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );

    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getUtilisateurFormateur_Success() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        this.mvc.perform(
                        get("/utilisateur")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getUtilisateurBN_Success() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        this.mvc.perform(
                        get("/utilisateur")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getUtilisateurDemande_Success() throws Exception {
        String accessToken = getLoginAccessToken("assoa", password);
        this.mvc.perform(
                        get("/utilisateur/demandesFavorables")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("demandesFavorables"))
                );

    }
    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getUtilisateurDemande_MauvaisUtilisateur() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        this.mvc.perform(
                        get("/utilisateur/demandesFavorables")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest()
                );

    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getFormateurInformation_Success() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        this.mvc.perform(
                        get("/utilisateur/formateur")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("nom"))
                );
    }
    @Test
    @Sql("classpath:test/data-user-test.sql")
    void getFormateurInformation_MauvaisUtilisateur() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        this.mvc.perform(
                        get("/utilisateur/formateur")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putUtilisateurAsso() throws Exception {
        UtilisateurApi utilisateurApi= new UtilisateurApi();
        AssociationApi associationApi = new AssociationApi();
        associationApi.setAcronyme("TEST");
        associationApi.setNomComplet("testnomcomplet");
        associationApi.setCollege(College.B);
        associationApi.setVille("TestVille");
        utilisateurApi.setEmail("test@yopmail.com");
        utilisateurApi.setNomUtilisateur("asso");
        utilisateurApi.setAssociation(associationApi);
        String accessToken = getLoginAccessToken("asso", password);
        String request = JsonUtils.objectToJson(utilisateurApi);
        this.mvc.perform(
                        put("/utilisateur/modification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putUtilisateurFormateur() throws Exception {
        UtilisateurApi utilisateurApi= new UtilisateurApi();
        FormateurApi formateurApi = new FormateurApi();
        formateurApi.setNom("testnom");
        formateurApi.setPrenom("testprenom");
        DomaineApi domaineApi = new DomaineApi();
        domaineApi.setCode("1");
        domaineApi.setLibelle("Administration associatif");
        domaineApi.setDescription("Comment gérer votre association administrativement.");
        List<DomaineApi> listDomaines = new ArrayList<>();
        listDomaines.add(domaineApi);
        formateurApi.setDomaines(listDomaines);
        utilisateurApi.setEmail("formateur@miage.net");
        utilisateurApi.setNomUtilisateur("formateur");
        utilisateurApi.setFormateur(formateurApi);
        String accessToken = getLoginAccessToken("formateur", password);
        String request = JsonUtils.objectToJson(utilisateurApi);
        this.mvc.perform(
                        put("/utilisateur/modification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putUtilisateurBN() throws Exception {
        UtilisateurApi utilisateurApi= new UtilisateurApi();
        MembreBureauNationalApi membreBureauNationalApi = new MembreBureauNationalApi();
        membreBureauNationalApi.setPoste("B");
        utilisateurApi.setEmail("bn@miage.net");
        utilisateurApi.setNomUtilisateur("bn");
        utilisateurApi.setMembreBureauNational(membreBureauNationalApi);
        String accessToken = getLoginAccessToken("bn", password);
        String request = JsonUtils.objectToJson(utilisateurApi);
        this.mvc.perform(
                        put("/utilisateur/modification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putModificationMailDejaUtilise() throws Exception {
        UtilisateurApi utilisateurApi= new UtilisateurApi();
        MembreBureauNationalApi membreBureauNationalApi = new MembreBureauNationalApi();
        membreBureauNationalApi.setPoste("B");
        utilisateurApi.setEmail("formateur@miage.net");
        utilisateurApi.setNomUtilisateur("bn");
        utilisateurApi.setMembreBureauNational(membreBureauNationalApi);
        String accessToken = getLoginAccessToken("bn", password);
        String request = JsonUtils.objectToJson(utilisateurApi);
        this.mvc.perform(
                        put("/utilisateur/modification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Email dÃ©j"))
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putModificationUsernameDejaUtilise() throws Exception {
        UtilisateurApi utilisateurApi= new UtilisateurApi();
        MembreBureauNationalApi membreBureauNationalApi = new MembreBureauNationalApi();
        membreBureauNationalApi.setPoste("B");
        utilisateurApi.setEmail("bn@miage.net");
        utilisateurApi.setNomUtilisateur("formateur");
        utilisateurApi.setMembreBureauNational(membreBureauNationalApi);
        String accessToken = getLoginAccessToken("bn", password);
        String request = JsonUtils.objectToJson(utilisateurApi);
        this.mvc.perform(
                        put("/utilisateur/modification")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Nom utilisateur dÃ©j"))
                );
    }
  
    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putUtilisateurPassword() throws Exception {
        UtilisateurChangePasswordApi utilisateurChangePasswordApi = new UtilisateurChangePasswordApi();
        utilisateurChangePasswordApi.setPassword(password);
        utilisateurChangePasswordApi.setNewPassword("testpassword");
        String accessToken = getLoginAccessToken("asso", password);
        String request = JsonUtils.objectToJson(utilisateurChangePasswordApi);
        this.mvc.perform(
                        put("/utilisateur/modification/motdepasse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void putUtilisateurPassword_BadPassword() throws Exception {
        UtilisateurChangePasswordApi utilisateurChangePasswordApi = new UtilisateurChangePasswordApi();
        utilisateurChangePasswordApi.setPassword("testbadpassword");
        utilisateurChangePasswordApi.setNewPassword("testpassword");
        String accessToken = getLoginAccessToken("asso", password);
        String request = JsonUtils.objectToJson(utilisateurChangePasswordApi);
        this.mvc.perform(
                        put("/utilisateur/modification/motdepasse")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .content(request)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest()
                );
    }
    
    private String getLoginAccessToken(String nomUtilisateur, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur(nomUtilisateur);
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        MvcResult result = this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        return json.getString("accessToken");
    }
}