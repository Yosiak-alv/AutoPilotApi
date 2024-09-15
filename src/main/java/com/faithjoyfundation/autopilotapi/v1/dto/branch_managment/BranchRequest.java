package com.faithjoyfundation.autopilotapi.v1.dto.branch_managment;

import com.faithjoyfundation.autopilotapi.v1.common.validations.IsEmail;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BranchRequest {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @NotEmpty
    @IsEmail(message = "invalid email format, example: josias@gmail.com")
    private String email;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[2678][0-9]{7}$", message = "invalid phone number, must be 8 digits long and start with 2, 6, 7 or 8")
    private String phone;

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 60)
    private String address;

    @NotNull
    private boolean main;

    @NotNull
    @Positive
    private Long municipalityId;
}
