package com.faithjoyfundation.autopilotapi.v1.dto.user_managment;

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
    @Email
    private String email;

    @NotNull
    @Positive
    private Long branchId;

    @Valid
    //@ValidArray(expectedType = Long.class, message = "Role IDs must be non-null and of type Integer")
    @Size(min = 1)
    private List<RoleRequest> roles;

}
