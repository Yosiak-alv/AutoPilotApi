package com.faithjoyfundation.autopilotapi.v1.dto.repair_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.Repair;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.RepairDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Valid
    @NotNull
    @Size(min = 1)
    private List<RepairDetailRequest> details = new ArrayList<>();

    public Set<RepairDetail> toRepairDetails(Repair repair) {
        return details.stream().map(detail -> detail.toRepairDetail(repair)).collect(Collectors.toSet());
    }
}