package com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairStatus;
import lombok.Data;

@Data
public class RepairStatusDTO {
    private Long id;
    private String name;

    public RepairStatusDTO(RepairStatus repairStatus) {
        this.id = repairStatus.getId();
        this.name = repairStatus.getName();
    }
}
