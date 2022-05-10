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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/application-test.properties")
class DonneeControllerTest {

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
    public void getAllDomaines() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        this.mvc.perform(
                        get("/data/domaines")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    public void getAllFormateurs() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        this.mvc.perform(
                        get("/data/formateurs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(
                        status().isOk()
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