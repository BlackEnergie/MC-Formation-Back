package com.mcformation.model.api;

import com.mcformation.model.database.MembreBureauNational;

public class UtilisateurApi {

    private String nomUtilisateur;

    private String email;

    private AssociationApi associationApi;

    private FormateurApi formateurApi;

    private MembreBureauNationalApi membreBureauNationalApi;

    public AssociationApi getAssociationApi() {
        return associationApi;
    }

    public void setAssociationApi(AssociationApi associationApi) {
        this.associationApi = associationApi;
    }

    public FormateurApi getFormateurApi() {
        return formateurApi;
    }

    public void setFormateurApi(FormateurApi formateurApi) {
        this.formateurApi = formateurApi;
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

    public MembreBureauNationalApi getMembreBureauNationalApi() {
        return membreBureauNationalApi;
    }

    public void setMembreBureauNationalApi(MembreBureauNationalApi membreBureauNationalApi) {
        this.membreBureauNationalApi = membreBureauNationalApi;
    }
}
