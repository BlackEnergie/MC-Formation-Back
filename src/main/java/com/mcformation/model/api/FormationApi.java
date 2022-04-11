package com.mcformation.model.api;

import com.mcformation.model.utils.StatutDemande;

import java.util.Date;
import java.util.List;

public class FormationApi {

    private Long id;

    // Formation
    private Float duree;
    private Date date;
    private String nom;
    private String type;
    private String prerequis;
    private String audience;
    private String parties;
    private String materiels;
    private String cadre;
    private List<FormateurApi> formateurs;

    // Demande
    private String sujet;
    private String detail;
    private List<DomaineApi> domaines;
    private Date dateDemande;
    private StatutDemande statut;
    private AssociationApi association;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<FormateurApi> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<FormateurApi> formateurs) {
        this.formateurs = formateurs;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<DomaineApi> getDomaines() {
        return domaines;
    }

    public void setDomaines(List<DomaineApi> domaines) {
        this.domaines = domaines;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public AssociationApi getAssociation() {
        return association;
    }

    public void setAssociation(AssociationApi association) {
        this.association = association;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }
}
