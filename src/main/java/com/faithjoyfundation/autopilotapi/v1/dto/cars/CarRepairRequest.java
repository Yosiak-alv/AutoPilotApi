package com.faithjoyfundation.autopilotapi.v1.dto.cars;

import com.faithjoyfundation.autopilotapi.v1.dto.repairs.RepairDetailRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class CarRepairRequest {
    @NotNull
    @Positive
    private Long workshopId;

    @NotNull
    @Positive
    private Long repairStatusId;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<RepairDetailRequest> details;

    public Double calculateTotal() {
        return details.stream().mapToDouble(RepairDetailRequest::getPrice).sum();
    }

}
