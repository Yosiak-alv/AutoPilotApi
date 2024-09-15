package com.faithjoyfundation.autopilotapi.v1.persistence.dto.brand_model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandRequest {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 25)
    private String name;
}
