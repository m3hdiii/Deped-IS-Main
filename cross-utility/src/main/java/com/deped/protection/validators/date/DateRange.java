package com.deped.protection.validators.date;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRange {

    String message() default "you have a wrong date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean required();
    int yearFrom();
    int monthFrom();
    int dayFrom();

    int yearTo() default -1;
    int monthTo() default -1;
    int dayTo() default -1;

}
