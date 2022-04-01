package com.mcformation.model.database;

import javax.persistence.*;

import com.mcformation.model.Erole;
import lombok.Getter;
import lombok.Setter;
import com.mcformation.model.Erole;



@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Erole nom;

}
