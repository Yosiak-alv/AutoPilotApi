package com.faithjoyfundation.autopilotapi.v1.dto.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModelCreateRequest {
    @NotNull()
    @NotEmpty
    //@UniqueName()
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Positive
    private Long brandId;
}
