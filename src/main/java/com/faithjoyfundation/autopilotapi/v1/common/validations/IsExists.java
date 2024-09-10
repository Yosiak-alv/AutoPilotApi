package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.*;

@Constraint(validatedBy = IsEnumValidator.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IsExists {
    String message() default "the value does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> repository();
}
