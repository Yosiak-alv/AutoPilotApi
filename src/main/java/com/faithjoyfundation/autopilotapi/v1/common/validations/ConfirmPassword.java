package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfirmPassword {
    String message() default "New password and confirm password do not match.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
