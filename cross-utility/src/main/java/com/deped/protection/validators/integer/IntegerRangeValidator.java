package com.deped.protection.validators.integer;

import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerRangeValidator implements ConstraintValidator<IntegerRange, Integer> {

    private static final Log log = LoggerFactory.make();
    private Integer min;
    private Integer max;
    boolean isMandatory;

    @Override
    public void initialize(IntegerRange property) {
        min = property.min();
        max = property.max();
        isMandatory = property.mandatory();
        validateParameters();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return !isMandatory;
        }


        return value >= min && value <= max;
    }

    private void validateParameters() {
        if (max < min) {
            throw log.getLengthCannotBeNegativeException();
        }
    }
}
