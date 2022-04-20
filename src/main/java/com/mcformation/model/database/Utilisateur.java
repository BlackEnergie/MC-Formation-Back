package com.mcformation.model.database;

import com.mcformation.model.database.auth.CreateUserToken;
import com.mcformation.model.database.auth.PasswordResetToken;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nomUtilisateur;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne
    // TODO: passer en ManyToOne
    private Role roles;

    @OneToOne(mappedBy = "utilisateur")
    @PrimaryKeyJoinColumn
    private Association association;

    @OneToOne(mappedBy = "utilisateur")
    @PrimaryKeyJoinColumn
    private Formateur formateur;

    @OneToOne(mappedBy = "utilisateur")
    @PrimaryKeyJoinColumn
    private MembreBureauNational membreBureauNational;

    @OneToOne(mappedBy = "utilisateur")
    private PasswordResetToken passwordResetToken;

    public Utilisateur(String nomUtilisateur, String email, String password) {
        this.nomUtilisateur = nomUtilisateur;
        this.password = password;
        this.email = email;
    }
    public Utilisateur(){
        
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return roles;
    }

    public void setRole(Role roles) {
        this.roles = roles;
    }

    public Association getAssociation() {
        return association;
    }

    public void setAssociation(Association association) {
        this.association = association;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public MembreBureauNational getMembreBureauNational() {
        return membreBureauNational;
    }

    public void setMembreBureauNational(MembreBureauNational membreBureauNational) {
        this.membreBureauNational = membreBureauNational;
    }

    public PasswordResetToken getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

}