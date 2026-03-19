package com.abcaa.sistema_atividades.business.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class DurationValidator implements ConstraintValidator<ValidDuration, Integer> {

    private static final Set<Integer> VALID_DURATIONS = Set.of(30, 60, 90, 120, 150, 180, 210, 240, 300);

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && VALID_DURATIONS.contains(value);
    }
}
