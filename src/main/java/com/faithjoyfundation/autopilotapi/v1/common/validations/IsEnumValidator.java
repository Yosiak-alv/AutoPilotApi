package com.faithjoyfundation.autopilotapi.v1.common.validations;

import jakarta.validation.ConstraintValidator;

import java.util.ArrayList;
import java.util.List;

public class IsEnumValidator implements ConstraintValidator<IsEnum, String> {

    private List<String> values = new ArrayList<>();
    @Override
    public void initialize(IsEnum enumValue) {
        values = new ArrayList<>();
        Enum<?> [] enumConstants = enumValue.enumClass().getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            values.add(enumConstant.toString());
        }
    }

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return values.contains(value);
    }
}