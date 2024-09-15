package com.faithjoyfundation.autopilotapi.v1.persistence.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @Email
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
}
