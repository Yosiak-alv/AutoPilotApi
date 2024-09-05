package com.faithjoyfundation.autopilotapi.v1.dto.cars;

import com.faithjoyfundation.autopilotapi.v1.common.validation.IsEnum;
import com.faithjoyfundation.autopilotapi.v1.enums.CarColor;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CarRequest {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[A-Z0-9]{7}$", message = "Plates must have 7 characters")
    private String plates;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[A-HJ-NPR-Z0-9]{17}", message = "VIN must have 17 characters")
    private String VIN;

    @NotNull
    @Positive
    private Double mileage;

    @NotNull
    @Positive
    private Integer year;

    @NotNull
    @IsEnum(enumClass = CarColor.class, message = "Color must be one of the following: RED, BLUE, GREEN, YELLOW, BLACK, WHITE, SILVER, GREY, ORANGE, BROWN, PURPLE, PINK")
    private String color;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 17)
    private String motorId;

    @NotNull
    @Positive
    private Long modelId;
}
