package com.faithjoyfundation.autopilotapi.v1.dto.repairs;

import com.faithjoyfundation.autopilotapi.v1.dto.cars.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshops.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.models.Repair;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class RepairDTO {
    private Long id;

    private Double total;

    @JsonIgnoreProperties({"repairs"})
    private WorkShopDTO workshop;

    @JsonIgnoreProperties({"repairs"})
    private CarDTO car;

    private RepairStatusDTO status;

    private Set<RepairDetailsDTO> details;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public RepairDTO(Repair repair, boolean includeWorkshop, boolean includeCar, boolean includeStatus, boolean includeDetails) {
        this.id = repair.getId();
        this.total = repair.getTotal();
        this.created = repair.getCreatedAt();
        this.updated = repair.getUpdatedAt();
        if (includeWorkshop) {
            this.workshop = new WorkShopDTO(repair.getWorkshop(), false);
        }
        if (includeCar) {
            this.car = new CarDTO(repair.getCar(), false, false, false);
        }
        if (includeStatus) {
            this.status = new RepairStatusDTO(repair.getRepairStatus());
        }
        if (includeDetails) {
            repair.getRepairDetails().forEach(repairDetail -> {
                this.details.add(new RepairDetailsDTO(repairDetail));
            });
        }
    }
}
