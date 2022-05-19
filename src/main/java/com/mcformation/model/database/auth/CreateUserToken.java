package com.mcformation.model.database.auth;

import com.mcformation.model.utils.Erole;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class CreateUserToken {

    private static final int EXPIRATION = 3600 * 24 * 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private Erole role;
    private String email;
    private Timestamp expirationDate;

    @PrePersist
    protected void onCreate() {
        expirationDate = new Timestamp(System.currentTimeMillis()+EXPIRATION);
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Erole getRole() {
        return role;
    }

    public void setRole(Erole role) {
        this.role = role;
    }
}
