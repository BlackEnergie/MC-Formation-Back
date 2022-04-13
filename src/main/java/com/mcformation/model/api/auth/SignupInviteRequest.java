package com.mcformation.model.api.auth;

import com.mcformation.model.utils.Erole;
import org.hibernate.validator.constraints.NotBlank;

public class SignupInviteRequest {
    @NotBlank
    private String email;
    @NotBlank
    private Erole role;
}
