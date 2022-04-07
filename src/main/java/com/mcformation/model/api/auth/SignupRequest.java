package com.mcformation.model.api.auth;

import com.mcformation.model.database.Association;
import com.mcformation.model.database.Formateur;
import com.mcformation.model.database.MembreBureauNational;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import java.util.Set;

public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String nomUtilisateur;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 70)
    private String password;

    private Association association;

    private MembreBureauNational membreBureauNational;

    private Formateur formateur;

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public MembreBureauNational getMembreBureauNational() {
        return membreBureauNational;
    }

    public void setMembreBureauNational(MembreBureauNational membreBureauNational) {
        this.membreBureauNational = membreBureauNational;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }
}
