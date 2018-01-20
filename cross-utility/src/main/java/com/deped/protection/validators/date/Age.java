package com.deped.protection.validators.date;

import com.deped.protection.validators.decimal.DoubleRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleRangeValidator.class)
@Documented
public @interface Age {
    String message() default "you have a wrong date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int age();
}