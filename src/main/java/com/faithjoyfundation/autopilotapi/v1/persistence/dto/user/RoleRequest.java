package com.faithjoyfundation.autopilotapi.v1.persistence.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoleRequest {
    @NotNull
    @Positive
    private Long id;
}
