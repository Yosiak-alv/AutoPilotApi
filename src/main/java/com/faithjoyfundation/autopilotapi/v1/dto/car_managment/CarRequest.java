package com.faithjoyfundation.autopilotapi.v1.dto.car_managment;

import com.faithjoyfundation.autopilotapi.v1.common.enums.CarColor;
import com.faithjoyfundation.autopilotapi.v1.common.validations.IsEnum;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

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
    private Integer mileage;

    @NotNull
    @Positive
    @Range(min = 1900, max = 2026)
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

    @NotNull
    @Positive
    private Long branchId;


}
