package com.mcformation.model.database;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name="demande_id",referencedColumnName="id")
    private Demande demande;
    


}
