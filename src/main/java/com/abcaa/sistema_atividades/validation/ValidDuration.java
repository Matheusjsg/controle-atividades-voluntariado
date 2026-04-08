package com.abcaa.sistema_atividades.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = DurationValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDuration {
    String message() default "Duração inválida. Use: 30, 60, 90, 120, 150, 180, 210, 240 ou 300 minutos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
