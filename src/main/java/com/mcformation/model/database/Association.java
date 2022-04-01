package com.mcformation.model.database;

import com.mcformation.model.College;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Getter
@Setter
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String ville;
    private College college;
    private String acronyme;
    private String nomComplet;

    @OneToMany(targetEntity = Demande.class)
    private List<Demande> demandes;

}
