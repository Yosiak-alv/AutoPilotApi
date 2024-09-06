package com.faithjoyfundation.autopilotapi.v1.dto.repairs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RepairRequest {

    @NotNull
    @Positive
    private Long carId;

    @NotNull
    @Positive
    private Long workshopId;

    @NotNull
    @Positive
    private Long repairStatusId;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<RepairDetailRequest> details = new ArrayList<>();

    public Double calculateTotal() {
        return details.stream().mapToDouble(RepairDetailRequest::getPrice).sum();
    }
}
