package com.faithjoyfundation.autopilotapi.v1.dto.repair_managment;

import com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RepairListDTO {
    private Long id;
    private Double total;
    private WorkShopListDTO workshop;
    private String status;

    private Set<RepairDetailDTO> details;

    public RepairListDTO(Repair repair) {
        this.id = repair.getId();
        this.total = repair.getTotal();
        this.workshop = new WorkShopListDTO(repair.getWorkshop());
        this.status = repair.getRepairStatus().getName();
        this.details = repair.getRepairDetails().stream().map(RepairDetailDTO::new).collect(Collectors.toSet());
    }
}
