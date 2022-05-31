package com.mcformation.model.api.auth;

import com.mcformation.model.utils.Erole;

import java.util.Date;

public class CreateUserTokenApi {

    private Long id;
    private Erole role;
    private String email;
    private Date expirationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Erole getRole() {
        return role;
    }

    public void setRole(Erole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
