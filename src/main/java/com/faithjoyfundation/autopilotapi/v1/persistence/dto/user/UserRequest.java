package com.faithjoyfundation.autopilotapi.v1.persistence.dto.user;

import com.faithjoyfundation.autopilotapi.v1.common.validations.IsEmail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    @NotBlank
    @NotBlank
    @Size(min = 3, max = 25)
    private String name;

    @NotNull
    @NotBlank
    @IsEmail(message = "invalid email format, example: josias@gmail.com")
    private String email;

    @Valid
    @Size(min = 1)
    private List<RoleRequest> roles;
}
