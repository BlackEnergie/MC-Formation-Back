package com.mcformation.controller;

import com.mcformation.model.api.UtilisateurIdFormationIdApi;
import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.repository.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/application-test.properties")
class FormationControllerTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private FormationRepository formationRepository;

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
        formationRepository.deleteAll();
        formateurRepository.deleteAll();
        membreBureauNationalRepository.deleteAll();
        demandeRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getAllFormationsBN() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        this.mvc.perform(
                        get("/formations")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }
    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getAllFormationsAssociation() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        this.mvc.perform(
                        get("/formations")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }
    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getAllFormationsFormateur() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        this.mvc.perform(
                        get("/formations")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getFormation_BN() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getFormation_Formateur() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getFormation_Asso() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isForbidden()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void getFormationLimit_Asso() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/modal/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void putFormation() {
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAssociationInteresse_Success() throws Exception {
        String nomUtilisateur = "assoa";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(1L);
        utilisateurIdFormationIdApi.setIdUtilisateur(4L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/interesser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Ãªtes intÃ©ressÃ©"))

                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAssociationDesinteresse_Success() throws Exception {
        String nomUtilisateur = "asso";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(3L);
        utilisateurIdFormationIdApi.setIdUtilisateur(2L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/interesser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("plus intÃ©ressÃ©"))

                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAssociationInteresse_Failure_FormationInconnue() throws Exception {
        String nomUtilisateur = "assoa";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(5L);
        utilisateurIdFormationIdApi.setIdUtilisateur(4L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/interesser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Formation inconnue"))
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAssociationInteresse_Failure_AssoInconnu() throws Exception {
        String nomUtilisateur = "assoa";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(1L);
        utilisateurIdFormationIdApi.setIdUtilisateur(6L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/interesser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Association inconnu"))
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAssociationInteresse_Failure_DemandeCreerParAsso() throws Exception {
        String nomUtilisateur = "asso";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(1L);
        utilisateurIdFormationIdApi.setIdUtilisateur(2L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/interesser")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Une erreur est survenue"))
                );
    }


    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAffectationFormation_Affecter_Success() throws Exception {
        String nomUtilisateur = "formateur";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(2L);
        utilisateurIdFormationIdApi.setIdUtilisateur(3L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/affectation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("affectÃ©"))

                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAffectationFormation_Desaffecter_Success() throws Exception {
        String nomUtilisateur = "formateur";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(3L);
        utilisateurIdFormationIdApi.setIdUtilisateur(3L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/affectation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("retirÃ©"))
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAffectationFormation_Failure_FormationInconnue() throws Exception {
        String nomUtilisateur = "formateur";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(4L);
        utilisateurIdFormationIdApi.setIdUtilisateur(3L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/affectation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Formation inconnue"))
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAffectationFormation_Failure_FormateurInconnu() throws Exception {
        String nomUtilisateur = "formateur";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(3L);
        utilisateurIdFormationIdApi.setIdUtilisateur(32L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/affectation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("Formateur inconnu"))
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql", "classpath:test/data-formation-test.sql"})
    void postAffectationFormation_Failure_StatutIncorrect() throws Exception {
        String nomUtilisateur = "formateur";
        String accessToken = getLoginAccessToken(nomUtilisateur, password);
        UtilisateurIdFormationIdApi utilisateurIdFormationIdApi = new UtilisateurIdFormationIdApi();
        utilisateurIdFormationIdApi.setIdFormation(1L);
        utilisateurIdFormationIdApi.setIdUtilisateur(3L);
        String request = JsonUtils.objectToJson(utilisateurIdFormationIdApi);
        this.mvc.perform(
                        post("/formation/affectation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isBadRequest(),
                        result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("attribuer"))
                );
    }

    private String getLoginAccessToken(String nomUtilisateur, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur(nomUtilisateur);
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        MvcResult result = this.mvc.perform(post("/auth/signin").content(request).contentType(MediaType.APPLICATION_JSON)).andReturn();
        JSONObject json = new JSONObject(result.getResponse().getContentAsString());
        return json.getString("accessToken");
    }


}