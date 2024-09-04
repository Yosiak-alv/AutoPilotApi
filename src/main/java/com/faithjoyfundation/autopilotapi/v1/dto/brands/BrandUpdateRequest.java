package com.faithjoyfundation.autopilotapi.v1.dto.brands;

import com.faithjoyfundation.autopilotapi.v1.common.validation.UniqueName;
import com.faithjoyfundation.autopilotapi.v1.services.BrandService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandUpdateRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
}
