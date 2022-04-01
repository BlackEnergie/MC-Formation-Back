package com.mcformation.model.api;

import java.util.List;

import com.mcformation.model.database.Association;
import com.mcformation.model.database.Domaine;

public class DemandeApi {
    private String sujet;
    private String detail;
    private List<Domaine> domaines;
    private Association association;
    
    public String getSujet(){
        return sujet;
    }
    public String getDetail(){
        return detail;
    }
    public List<Domaine> getDomaines(){
        return domaines;
    }
    public Association getAssociation(){
        return association;
    }
    public void setSujet(String newSujet){
        this.sujet=newSujet;
    }
    public void setDetail(String newDetail){
        this.detail=newDetail;
    }
    public void setDomaines(List<Domaine> newDomaines){
        this.domaines=newDomaines;
    }
    public void setAssociation(Association newAssociation){
        this.association=newAssociation;
    }
    
}