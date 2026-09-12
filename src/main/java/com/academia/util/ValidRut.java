package com.academia.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RutValidator.class) // Apunta a la lógica de validación
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRut {
    String message() default "El RUT ingresado no es válido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}