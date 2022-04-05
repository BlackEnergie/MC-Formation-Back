package com.mcformation.model.database;

import com.mcformation.model.College;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Getter
@Setter
@Entity
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String ville;
    private College college;
    private String acronyme;
    private String nomComplet;
        
    public Association(String email, String ville, College college, String acronyme, String nomComplet){
        this.email = email;
        this.ville = ville;
        this.college = college;
        this.acronyme = acronyme;
        this.nomComplet = nomComplet;
    };

    @OneToMany(targetEntity=Utilisateur.class, mappedBy="association")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    public Association() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}