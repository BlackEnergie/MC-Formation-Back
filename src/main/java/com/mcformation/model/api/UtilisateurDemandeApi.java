package com.mcformation.model.api;

import java.util.List;

public class UtilisateurDemandeApi {

    String acronyme;

    private List<Long> demandesFavorables;

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public List<Long> getDemandesFavorables() {
        return demandesFavorables;
    }

    public void setDemandesFavorables(List<Long> demandesFavorables) {
        this.demandesFavorables = demandesFavorables;
    }
}