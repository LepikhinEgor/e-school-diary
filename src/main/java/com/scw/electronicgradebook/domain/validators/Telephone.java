package com.scw.electronicgradebook.domain.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TelephoneValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telephone {
    String message() default "The telephone number must contain only digits";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
