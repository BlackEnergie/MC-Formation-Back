package com.mcformation.model.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MembreBureauNational {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String poste;


    public MembreBureauNational (String email, String poste){
        this.email = email;
        this.poste = poste;

    }
    @OneToMany(targetEntity=Utilisateur.class, mappedBy="membreBureauNational")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public MembreBureauNational() {

    }
}
