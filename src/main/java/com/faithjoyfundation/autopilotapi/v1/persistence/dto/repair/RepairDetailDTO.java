package com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairDetail;
import lombok.Data;

@Data
public class RepairDetailDTO {
    private Long id;

    private String name;

    private String description;

    private Double price;

    public RepairDetailDTO(RepairDetail repairDetails) {
        this.id = repairDetails.getId();
        this.name = repairDetails.getName();
        this.description = repairDetails.getDescription();
        this.price = repairDetails.getPrice();
    }
}
