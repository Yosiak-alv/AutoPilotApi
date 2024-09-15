package com.faithjoyfundation.autopilotapi.v1.persistence.dto.repair;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.workshop.WorkShopListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class RepairListDTO {
    private Long id;
    private BigDecimal total;
    private WorkShopListDTO workshop;
    private RepairStatusDTO status;
    private Set<RepairDetailDTO> details;

    public RepairListDTO(Repair repair) {
        this.id = repair.getId();
        this.total = repair.getTotal();
        this.workshop = new WorkShopListDTO(repair.getWorkshop());
        this.status = new RepairStatusDTO(repair.getRepairStatus());
        this.details = repair.getRepairDetails().stream().map(RepairDetailDTO::new).collect(Collectors.toSet());
    }
}
