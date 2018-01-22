package com.deped.protection.validators.date;

import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<Age, Date> {
    private int age;
    private boolean isRequired;

    @Override
    public void initialize(Age age) {
        this.age = age.age();
        this.isRequired = age.required();
    }


    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        if (date == null) {
            if (isRequired) {
                return false;
            } else {
                return true;
            }
        }


        Date from = DateUtils.addYears(new Date(), -age);
        Date to = new Date();
        return (date.compareTo(to) <= 0) && date.compareTo(from) > 0;
    }
}