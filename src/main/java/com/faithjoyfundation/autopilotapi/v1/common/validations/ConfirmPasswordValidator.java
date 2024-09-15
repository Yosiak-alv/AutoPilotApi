package com.faithjoyfundation.autopilotapi.v1.common.validations;

import com.faithjoyfundation.autopilotapi.v1.dto.auth_managment.NewPasswordRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, NewPasswordRequest> {

    @Override
    public boolean isValid(NewPasswordRequest request, ConstraintValidatorContext context) {
        if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
            return false;
        }

        boolean valid = request.getNewPassword().equals(request.getConfirmPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword") // Point to the specific field
                    .addConstraintViolation();
        }
        return valid;
    }
}