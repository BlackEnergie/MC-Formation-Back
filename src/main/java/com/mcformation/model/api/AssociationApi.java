package com.mcformation.model.api;

import com.mcformation.model.database.Demande;
import com.mcformation.model.utils.College;

import java.util.List;

public class AssociationApi {

    private String ville;
    private College college;
    private String acronyme;
    private String nomComplet;

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
}
