package com.mcformation.model.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private Float duree;
    private String nom;
    private String type;
    private String prerequis;
    private String audience;
    private String parties;
    private String materiels;
    private String cadre;

    @OneToOne
    @JoinColumn(name = "demande_id", referencedColumnName = "id")
    private Demande demande;


}
