package com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRepairStatusRequest {
    @NotNull
    @Positive
    private Long repairStatusId;
}
