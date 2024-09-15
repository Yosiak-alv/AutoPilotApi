package com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairDetail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RepairDetailRequest {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 100)
    private String description;

    @NotNull
    @Positive
    private Double price;

    public RepairDetail toRepairDetail(Repair repair) {
        RepairDetail repairDetail = new RepairDetail();
        repairDetail.setRepair(repair);
        repairDetail.setName(name);
        repairDetail.setDescription(description);
        repairDetail.setPrice(price);
        return repairDetail;
    }
}
