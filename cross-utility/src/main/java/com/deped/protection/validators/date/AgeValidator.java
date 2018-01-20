package com.deped.protection.validators.date;

import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<Age, Date> {
    private int age;

    @Override
    public void initialize(Age age) {
        this.age = age.age();
    }


    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        Date from = DateUtils.addYears(new Date(), -age);
        Date to = new Date();
        return date.before(to) && date.after(from);
    }
}