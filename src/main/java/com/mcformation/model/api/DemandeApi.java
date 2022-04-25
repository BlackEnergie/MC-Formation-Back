package com.mcformation.model.api;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

public class DemandeApi {

    @NotBlank(message = "Veuillez renseigner le sujet de la demande de formation.")
    private String sujet;
    @NotBlank(message = "Veuillez renseigner le détail de votre demande de formation.")
    private String detail;
    @NotEmpty(message = "Veuillez renseigner au moins un domaine de formation.")
    private List<DomaineApi> domaines;
    private Date dateDemande;
    private String nomUtilisateur;

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

    public Date getDateDemande(){
        return dateDemande;
    }
    
    public void setDateDemande(Date dateDemande){
        this.dateDemande = dateDemande;
    }


    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }
}