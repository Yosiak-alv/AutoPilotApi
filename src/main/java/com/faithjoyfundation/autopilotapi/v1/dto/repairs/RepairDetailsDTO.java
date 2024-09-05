package com.faithjoyfundation.autopilotapi.v1.dto.repairs;

import com.faithjoyfundation.autopilotapi.v1.models.RepairDetail;
import lombok.Data;

@Data
public class RepairDetailsDTO {
    private Long id;

    private String name;

    private String description;

    private Double price;

    public RepairDetailsDTO(RepairDetail repairDetails) {
        this.id = repairDetails.getId();
        this.name = repairDetails.getName();
        this.description = repairDetails.getDescription();
        this.price = repairDetails.getPrice();
    }
}
