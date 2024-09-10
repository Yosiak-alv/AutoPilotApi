package com.faithjoyfundation.autopilotapi.v1.dto.repair_managment;

import com.faithjoyfundation.autopilotapi.v1.common.validations.IsExists;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairStatusRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRepairStatusRequest {
    @NotNull
    @Positive
    //@IsExists(message = "repair status does not exist", repository = RepairStatusRepository.class)
    private Long repairStatusId;
}
