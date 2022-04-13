package com.mcformation.model.api.auth;

import com.mcformation.model.utils.Erole;
import org.hibernate.validator.constraints.NotBlank;

public class SignupInviteRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
