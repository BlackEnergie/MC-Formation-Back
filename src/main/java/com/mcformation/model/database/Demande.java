package com.mcformation.model.database;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateDemande;

    @ManyToMany
    private List<Domaine> domaines;

    @ManyToMany
    private List<Association> associationsFavorables;

}
