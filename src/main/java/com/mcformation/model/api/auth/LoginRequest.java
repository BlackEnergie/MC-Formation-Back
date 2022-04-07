package com.mcformation.model.api.auth;

import javax.validation.constraints.NotBlank;
public class LoginRequest {

    @NotBlank
    private String nomUtilisateur;

    @NotBlank
    private String password;

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
