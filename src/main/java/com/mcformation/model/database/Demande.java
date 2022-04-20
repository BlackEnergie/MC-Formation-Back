package com.mcformation.model.database;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;

import com.mcformation.model.utils.StatutDemande;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sujet;
    private String detail;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    @CreationTimestamp
    private Date dateDemande;

    @ManyToMany
    private List<Domaine> domaines;

    @OneToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
    
    @ManyToMany
    private List<Association> associationsFavorables;

    @PrePersist
    protected void onCreate() {
        statut = StatutDemande.DEMANDE;
    }

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

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public StatutDemande getStatut() {
        return statut;
    }

    public void setStatut(StatutDemande statut) {
        this.statut = statut;
    }
}
