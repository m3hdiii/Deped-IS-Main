package com.deped.protection.validators.enums;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private List<String> valueList = null;


    @Override
    public void initialize(EnumValidator constraintAnnotation) {

        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClazz();
        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        if (!valueList.contains(value.toUpperCase())) {
            return false;
        }
        return true;
    }
}
