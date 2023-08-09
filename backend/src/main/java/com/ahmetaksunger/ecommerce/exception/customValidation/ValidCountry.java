package com.ahmetaksunger.ecommerce.exception.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CountryValidator.class })
public @interface ValidCountry {
    String message() default "Invalid country";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
