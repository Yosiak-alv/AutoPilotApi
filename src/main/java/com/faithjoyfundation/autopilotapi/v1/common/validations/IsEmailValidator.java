package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class IsEmailValidator implements ConstraintValidator<IsEmail, String> {

    private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final Set<String> ALLOWED_DOMAINS = Set.of("gmail.com", "outlook.com", "yahoo.com", "hotmail.com");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return false; // Handle the case where the email is null or blank
        }
        if (!PATTERN.matcher(email).matches()) {
            return false;
        }
        String domain = email.substring(email.lastIndexOf("@") + 1);
        return ALLOWED_DOMAINS.contains(domain);
    }
}