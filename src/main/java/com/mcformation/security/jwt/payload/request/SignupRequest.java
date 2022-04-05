package com.mcformation.security.jwt.payload.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.mcformation.repository.AssociationRepository;

import java.util.Set;

public class SignupRequest {

        @NotBlank
        @Size(min = 3, max = 20)
        private String nomUtilisateur;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        private Set<String> role;

        @NotBlank
        @Size(min = 6, max = 70)
        private String password;

        private String nomAssociation ;

        private String nomFormateur;
    
        private String posteMembreBureauNational;


        public String getNomUtilisateur() {
            return nomUtilisateur;
        }

        public String getEmail() {
        return email;
    }

        public void setEmail(String email) {
        this.email =  email;
    }


        public void setNomUtilisateur(String nomUtilisateur) {
            this.nomUtilisateur = nomUtilisateur;
        }

        public Set<String> getRole() {
            return this.role;
        }

        public void setRole(Set<String> role) {
            this.role = role;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNomAssociation() {
            return this.nomAssociation;
        }

        public void setAssociation(String nomAssociation) {
            this.nomAssociation = nomAssociation;
        }

        public String getNomFormateur() {
            return nomFormateur;
        }

        public void setFormateur(String nomFormateur) {
            this.nomFormateur = nomFormateur;
        }

        public String getPosteMembreBureauNational() {
            return this.posteMembreBureauNational;
        }

        public void setMembreBureauNational(String posteMembreBureauNational) {
            this.posteMembreBureauNational = posteMembreBureauNational;
        }
}
