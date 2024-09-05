package com.faithjoyfundation.autopilotapi.v1.dto.workshops;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class WorkShopCreateRequest {
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
    @Pattern(regexp = "^[2678][0-9]{7}$", message = "invalid phone number")
    private String phone;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 60)
    private String address;

    @NotNull
    @Positive
    private Long municipalityId;
}
