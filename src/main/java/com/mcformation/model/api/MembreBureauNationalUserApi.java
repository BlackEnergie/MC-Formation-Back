package com.mcformation.model.api;

public class MembreBureauNationalUserApi {

    private Long id;

    private String email;

    private String nomUtilisateur;

    private  Boolean actif;

    private String poste;

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

    public Boolean getActif() {return actif;}

    public void setActif(Boolean actif) {this.actif = actif;}
    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
