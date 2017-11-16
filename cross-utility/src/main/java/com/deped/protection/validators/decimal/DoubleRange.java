package com.deped.protection.validators.decimal;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleRangeValidator.class)
@Documented
public @interface DoubleRange {
    String message() default "you have a wrong decimal range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default 0.0;

    double max() default Double.MAX_VALUE;

}
