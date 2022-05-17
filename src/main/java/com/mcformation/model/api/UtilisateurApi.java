package com.mcformation.model.api;

import com.mcformation.model.database.MembreBureauNational;

public class UtilisateurApi {

    private String nomUtilisateur;

    private String email;

    private AssociationApi associationApi;

    private FormateurApi formateurApi;

    private MembreBureauNational membreBureauNational;

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

    public MembreBureauNational getMembreBureauNational() {
        return membreBureauNational;
    }

    public void setMembreBureauNational(MembreBureauNational membreBureauNational) {
        this.membreBureauNational = membreBureauNational;
    }
}
