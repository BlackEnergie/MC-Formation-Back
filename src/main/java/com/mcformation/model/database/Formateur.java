package com.mcformation.model.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Formateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private Date dateCreation;
    private String nom;
    private String prenom;

    @ManyToMany
    @JoinTable(
            name = "formateur_domaine",
            joinColumns = @JoinColumn(name = "formateur_id"),
            inverseJoinColumns = @JoinColumn(name = "domaine_id")
    )
    private List<Domaine> domaines;

}
