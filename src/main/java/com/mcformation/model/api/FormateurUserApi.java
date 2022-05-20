package com.mcformation.model.api;

import java.util.List;

public class FormateurUserApi {

    private Long id;
    private String nom;
    private String prenom;

    private String nomUtilisateur;

    private String email;

    private List<DomaineApi> domaines;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public List<DomaineApi> getDomaines() {
        return domaines;
    }

    public void setDomaines(List<DomaineApi> domaines) {
        this.domaines = domaines;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
