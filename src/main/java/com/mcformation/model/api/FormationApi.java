package com.mcformation.model.api;

import java.util.Date;
import java.util.List;

public class FormationApi {
   
    private Float duree;
    private Date date;
    private String nom;
    private String type;
    private String prerequis;
    private String audience;
    private String parties;
    private String materiels;
    private String cadre;
    private DemandeApi demande;
    
    private List<FormateurApi> formateurs;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrerequis() {
        return prerequis;
    }

    public void setPrerequis(String prerequis) {
        this.prerequis = prerequis;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getParties() {
        return parties;
    }

    public void setParties(String parties) {
        this.parties = parties;
    }

    public String getMateriels() {
        return materiels;
    }

    public void setMateriels(String materiels) {
        this.materiels = materiels;
    }

    public String getCadre() {
        return cadre;
    }

    public void setCadre(String cadre) {
        this.cadre = cadre;
    }

    public DemandeApi getDemande() {
        return demande;
    }

    public void setDemande(DemandeApi demande) {
        this.demande = demande;
    }
    public List<FormateurApi> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<FormateurApi> formateurs) {
        this.formateurs = formateurs;
    }
}
