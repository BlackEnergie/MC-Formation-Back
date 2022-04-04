package com.mcformation.security.jwt.payload.response;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String nomUtilisateur;
    private String email;
    private List<String> roles;
    private String nomAssociation;
    private String nomFormateur;
    private String posteMembreBureauNational;

    public JwtResponse(String accessToken, Long id, String nomUtilisateur, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public List<String> getRoles() {
        return roles;
    }
}
