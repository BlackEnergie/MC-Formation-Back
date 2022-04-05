package com.mcformation.model.api;


public class DomaineApi {
    private String code;
    private String libelle;
    private String description;

    public String getCode() {
        return code;
    }
    public void setCode(String newCode) {
        this.code = newCode;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String newLibelle) {
        this.libelle = newLibelle;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
