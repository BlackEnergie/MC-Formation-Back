package com.mcformation.model.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Domaine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;
    private String description;

    @Column(unique = true)
    private String code;

    @ManyToMany(mappedBy = "domaines")
    private List<Formateur> formateurs;
}
