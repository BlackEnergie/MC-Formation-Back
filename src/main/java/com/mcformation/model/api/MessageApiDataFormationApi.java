package com.mcformation.model.api;

public class MessageApiDataFormationApi extends MessageApi {

    private FormationApi formation;

    public FormationApi getFormation() {
        return formation;
    }

    public void setFormation(FormationApi formation) {
        this.formation = formation;
    }
}
