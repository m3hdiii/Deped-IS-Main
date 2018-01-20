package com.deped.protection.validators.date;

import com.deped.protection.validators.decimal.DoubleRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Calendar;
import java.util.Date;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoubleRangeValidator.class)
@Documented
public @interface DateRange {

    final Date dateDefault = new Date();
    final int year = dateDefault.getYear();
    String message() default "you have a wrong date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int yearFrom();
    int monthFrom();
    int dayFrom();

    int yearTo() default -1;
    int monthTo() default -1;
    int dayTo() default -1;

}
