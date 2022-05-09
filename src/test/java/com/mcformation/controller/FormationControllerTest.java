package com.mcformation.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        demandeRepository.deleteAll();
        formationRepository.deleteAll();
        associationRepository.deleteAll();
        formateurRepository.deleteAll();
        membreBureauNationalRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    void getAllFormations() {
        // TODO: quand le back sera modifié
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql" ,"classpath:test/data-formation-test.sql"})
    void getFormation_BN() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql" ,"classpath:test/data-formation-test.sql"})
    void getFormation_Formateur() throws Exception {
        String accessToken = getLoginAccessToken("formateur", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql" ,"classpath:test/data-formation-test.sql"})
    void getFormation_Asso() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isUnauthorized()
                );
    }

    @Test
    @Sql({"classpath:test/data-user-test.sql" ,"classpath:test/data-formation-test.sql"})
    void getFormationLimit_Asso() throws Exception {
        String accessToken = getLoginAccessToken("asso", password);
        String id = "1";
        this.mvc.perform(
                        get("/formation/details/" + id)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void putFormation() {
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