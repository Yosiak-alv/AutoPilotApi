package com.faithjoyfundation.autopilotapi.v1.dto.workshop_managment;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WorkShopRequest {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[2678][0-9]{7}$", message = "numero de telefono invalido (debe empezar con 2, 6, 7 u 8)")
    private String phone;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 60)
    private String address;

    @NotNull
    @Positive
    private Long municipalityId;
}