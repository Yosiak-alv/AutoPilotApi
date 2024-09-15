package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class IsStrongPasswordValidator implements ConstraintValidator<IsStrongPassword, String> {

    private static final String PASSWORD_PATTERN = "((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).{8,}$";

    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            return false; // handle null or blank password
        }
        return pattern.matcher(password).matches();
    }
}
