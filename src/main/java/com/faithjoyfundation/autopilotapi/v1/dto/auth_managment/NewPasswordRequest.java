package com.faithjoyfundation.autopilotapi.v1.dto.auth_managment;

import com.faithjoyfundation.autopilotapi.v1.common.validations.ConfirmPassword;
import com.faithjoyfundation.autopilotapi.v1.common.validations.IsStrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@ConfirmPassword
public class NewPasswordRequest {
    @NotNull
    @NotBlank
    private String oldPassword;

    @NotNull
    @NotBlank
    @IsStrongPassword
    private String newPassword;

    @NotNull
    @NotBlank
    private String confirmPassword;
}
