package com.faithjoyfundation.autopilotapi.v1.dto.repair_managment;

import com.faithjoyfundation.autopilotapi.v1.dto.car_managment.CarDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RepairDTO {
    private Long id;

    private Double total;

    @JsonIgnoreProperties({"created", "updated"})
    private WorkShopDTO workshop;

    @JsonIgnoreProperties({"created", "updated"})
    private CarDTO car;

    private RepairStatusDTO status;

    private Set<RepairDetailDTO> details;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updated;

    public RepairDTO(Repair repair) {
        this.id = repair.getId();
        this.total = repair.getTotal();
        this.created = repair.getCreatedAt();
        this.updated = repair.getUpdatedAt();
        this.workshop = new WorkShopDTO(repair.getWorkshop());
        this.car = new CarDTO(repair.getCar());
        this.status = new RepairStatusDTO(repair.getRepairStatus());
        this.details = repair.getRepairDetails().stream().map(RepairDetailDTO::new).collect(Collectors.toSet());
    }
}
