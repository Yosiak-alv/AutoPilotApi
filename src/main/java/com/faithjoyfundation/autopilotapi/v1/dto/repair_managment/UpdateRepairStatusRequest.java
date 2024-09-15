package com.faithjoyfundation.autopilotapi.v1.dto.repair_managment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRepairStatusRequest {
    @NotNull
    @Positive
    private Long repairStatusId;
}
