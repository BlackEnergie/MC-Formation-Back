package com.mcformation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcformation.model.Erole;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.Role;
import com.mcformation.repository.RoleRepository;
import com.mcformation.security.jwt.payload.request.SignupRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class AuthControllerTest {

    @Autowired
    AuthController authController;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    private String email = "email";
    private String nomUtilisateur = "user";
    private String motDePasse = "password";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.nouveauNomUtilisateur();
        this.nouvelEmail();
        this.configurationBaseDeDonnees();
    }

    private void nouvelEmail() {
        email += "A";
    }

    private void nouveauNomUtilisateur() {
        nomUtilisateur += "A";
    }

    private boolean testRoleConfiguration() {
        return roleRepository.count() == 3;
    }

    private void configurationBaseDeDonnees() {
        if (!testRoleConfiguration()) {
            configurationRoles();
        }
    }

    private void configurationRoles() {
        Role roleAsso = new Role();
        roleAsso.setNom(Erole.ROLE_ASSO);
        Role roleFormateur = new Role();
        roleFormateur.setNom(Erole.ROLE_FORMATEUR);
        Role roleBn = new Role();
        roleBn.setNom(Erole.ROLE_BN);
        roleRepository.save(roleAsso);
        roleRepository.save(roleFormateur);
        roleRepository.save(roleBn);
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("authController"));
    }

    @Test
    public void nouveauFormateur_reponse200() throws Exception {
        SignupRequest signupRequest = nouvelleSignupRequestFormateur("Roger", "Hugo");
        String request = objectMapper.writeValueAsString(signupRequest);
        this.mvc.perform(post("/api/auth/signup")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private SignupRequest getSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail(email);
        request.setPassword(motDePasse);
        request.setNomUtilisateur(nomUtilisateur);
        return request;
    }

    private SignupRequest nouvelleSignupRequestFormateur(String nom, String prenom) {
        Formateur formateur = new Formateur();
        formateur.setNom(nom);
        formateur.setPrenom(prenom);
        SignupRequest signupRequest = getSignupRequest();
        signupRequest.setFormateur(formateur);
        return signupRequest;
    }

}
