package com.mcformation.model.api;

public class UtilisateurApi {

    private String nomUtilisateur;

    private String email;

    private AssociationApi association;

    private FormateurApi formateur;

    private MembreBureauNationalApi membreBureauNational;

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

    public AssociationApi getAssociation() {
        return association;
    }

    public void setAssociation(AssociationApi association) {
        this.association = association;
    }

    public FormateurApi getFormateur() {
        return formateur;
    }

    public void setFormateur(FormateurApi formateur) {
        this.formateur = formateur;
    }

    public MembreBureauNationalApi getMembreBureauNational() {
        return membreBureauNational;
    }

    public void setMembreBureauNational(MembreBureauNationalApi membreBureauNational) {
        this.membreBureauNational = membreBureauNational;
    }

}
