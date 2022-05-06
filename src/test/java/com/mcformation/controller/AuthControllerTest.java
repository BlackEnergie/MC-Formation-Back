package com.mcformation.controller;

import com.mcformation.model.api.auth.LoginRequest;
import com.mcformation.model.api.auth.SignupInviteRequest;
import com.mcformation.model.utils.Erole;
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
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test/application-test.properties")
class AuthControllerTest {

    @Autowired
    AuthController authController;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private FormateurRepository formateurRepository;

    @Autowired
    private MembreBureauNationalRepository membreBureauNationalRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

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
        userTokenRepository.deleteAll();
        utilisateurRepository.deleteAll();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("authController"));
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_Bn_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur("bn");
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.accessToken").exists()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_Asso_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur("asso");
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.accessToken").exists()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_Formateur_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur("formateur");
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        MockMvcResultMatchers.jsonPath("$.accessToken").exists()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_BadPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur("asso");
        loginRequest.setPassword("test");
        String request = JsonUtils.objectToJson(loginRequest);
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_BadUsername() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setNomUtilisateur("test");
        loginRequest.setPassword(password);
        String request = JsonUtils.objectToJson(loginRequest);
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void authenticateUser_BadRequest() throws Exception {
        String request = "";
        this.mvc.perform(
                        post("/auth/signin")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void inviteUtilisateur_Success() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        SignupInviteRequest signupInviteRequest = new SignupInviteRequest();
        signupInviteRequest.setEmail("email@email.com");
        signupInviteRequest.setRole(Erole.ROLE_FORMATEUR);
        String request = JsonUtils.objectToJson(signupInviteRequest);
        this.mvc.perform(
                        post("/auth/signup/invite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void inviteUtilisateur_EmailInvalid() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        SignupInviteRequest signupInviteRequest = new SignupInviteRequest();
        signupInviteRequest.setEmail("mail@mail");
        signupInviteRequest.setRole(Erole.ROLE_FORMATEUR);
        String request = JsonUtils.objectToJson(signupInviteRequest);
        this.mvc.perform(
                        post("/auth/signup/invite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    @Sql("classpath:test/data-user-test.sql")
    void inviteUtilisateur_UserAlreadyExists() throws Exception {
        String accessToken = getLoginAccessToken("bn", password);
        SignupInviteRequest signupInviteRequest = new SignupInviteRequest();
        signupInviteRequest.setEmail("bn@miage.net");
        signupInviteRequest.setRole(Erole.ROLE_FORMATEUR);
        String request = JsonUtils.objectToJson(signupInviteRequest);
        this.mvc.perform(
                        post("/auth/signup/invite")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                                .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    @Sql("classpath:test/data-user-invite.sql")
    void checkToken_Success() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        this.mvc.perform(
                        post("/auth/signup/checkToken?token=" + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    @Sql("classpath:test/data-user-invite.sql")
    void checkToken_Error() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ8";
        this.mvc.perform(
                        post("/auth/signup/checkToken?token=" + token)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpectAll(
                        status().is4xxClientError()
                );
    }

    @Test
    void creationUtilisateur() {
    }

    @Test
    void registerUser() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void checkPasswordTokenValid() {
    }

    @Test
    void savePassword() {
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