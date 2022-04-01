package com.mcformation.model.database;

import javax.persistence.*;

@Entity
public class Domaine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;
    private String description;

    @Column(unique = true)
    private String code;

    public Long getId(){
        return id;
    }
    public String getLibelle(){
        return libelle;
    }
    public String getDescription(){
        return description;
    }
    public String getCode(){
        return code;
    }
    public void setId(Long newId){
        this.id=newId;
    }
    public void setLibelle(String newLibelle){
        this.libelle=newLibelle;
    }
    public void setDescription(String newDescription){
        this.description=newDescription;
    }
    public void setCode(String newCode){
        this.code=newCode;
    }
    

}
