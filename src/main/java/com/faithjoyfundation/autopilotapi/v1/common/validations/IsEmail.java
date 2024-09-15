package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = IsEmailValidator.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsEmail {
    String message() default "invalid email format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}