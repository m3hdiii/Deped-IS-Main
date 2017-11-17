package com.deped.protection.validators.decimal;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DoubleRangeValidator implements ConstraintValidator<DoubleRange, Double> {

    private static final Log log = LoggerFactory.make();
    private double min;
    private double max;

    @Override
    public void initialize(DoubleRange parameters) {
        min = parameters.min();
        max = parameters.max();
        validateParameters();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value > min && value < max;

    }

    private void validateParameters() {
        if (max < min) {
            throw log.getLengthCannotBeNegativeException();
        }
    }
}
