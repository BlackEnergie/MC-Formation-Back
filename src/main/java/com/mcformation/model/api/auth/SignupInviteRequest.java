package com.mcformation.model.api.auth;

import com.mcformation.model.utils.Erole;
import org.hibernate.validator.constraints.NotBlank;

public class SignupInviteRequest {
    @NotBlank
    private String email;
    @NotBlank
    private Erole role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Erole getRole() {
        return role;
    }

    public void setRole(Erole role) {
        this.role = role;
    }
}
