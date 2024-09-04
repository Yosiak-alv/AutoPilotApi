package com.faithjoyfundation.autopilotapi.v1.dto.brands;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class BrandCreateRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
}
