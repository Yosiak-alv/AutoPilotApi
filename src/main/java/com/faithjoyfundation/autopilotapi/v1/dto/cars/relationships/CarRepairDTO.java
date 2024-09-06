package com.faithjoyfundation.autopilotapi.v1.dto.cars.relationships;

import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDetailsDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairStatusDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Repair;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class CarRepairDTO {
    private Long id;

    private Double total;

    private WorkShopDTO workshop;

    private RepairStatusDTO status;

    private Set<RepairDetailsDTO> details = new HashSet<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public CarRepairDTO(Repair repair) {
        this.id = repair.getId();
        this.total = repair.getTotal();
        this.created = repair.getCreatedAt();
        this.updated = repair.getUpdatedAt();
        this.workshop = new WorkShopDTO(repair.getWorkshop());
        this.status = new RepairStatusDTO(repair.getRepairStatus());
        repair.getRepairDetails().forEach(repairDetail -> {
            this.details.add(new RepairDetailsDTO(repairDetail));
        });

    }
}
