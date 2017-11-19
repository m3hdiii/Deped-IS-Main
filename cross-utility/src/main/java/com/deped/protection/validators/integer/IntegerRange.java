package com.deped.protection.validators.integer;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerRangeValidator.class)
@Documented
public @interface IntegerRange {
    String message() default "you have a wrong decimal range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    boolean mandatory() default false;

}
