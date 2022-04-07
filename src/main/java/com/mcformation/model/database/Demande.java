package com.mcformation.model.database;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    private Date dateDemande;
    private String sujet;
    private String detail;

    @ManyToMany
    private List<Domaine> domaines;

    @ManyToMany
    private List<Association> associationsFavorables;

    public Long getId(){
        return id;
    }
    public Date getDateDemande(){
        return dateDemande;
    }
    public String getSujet(){
        return sujet;
    }
    public String getDetail(){
        return detail;
    }
    public List<Domaine> getDomaines(){
        return domaines;
    }
    public List<Association> getAssociationsFavorables(){
        return associationsFavorables;
    }
    public void setId(Long id){
        this.id=id;
    }
    public void setDateDemande(Date newDate){
        this.dateDemande=newDate;
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
    public void setAssociationsFavorables(List<Association> newAssociationsFavorables){
        this.associationsFavorables=newAssociationsFavorables;
    }
    
}
