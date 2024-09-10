package com.faithjoyfundation.autopilotapi.v1.common.validations;

import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RepairStatusRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IsExistsValidator implements ConstraintValidator<IsExists, Long> {

    @Autowired
    private RepairStatusRepository repairStatusRepository;

    @Override
    public void initialize(IsExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (repairStatusRepository == null) {
            return true;
        }
        return repairStatusRepository.existsById(value);
    }
}
