package com.mcformation.security.jwt.payload.request;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignupRequest {

        @NotBlank
        @Size(min = 3, max = 20)
        private String nomUtilisateur;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NotBlank
        @Size(min = 6, max = 40)
        private String password;

        private Set<String> role;

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

}
