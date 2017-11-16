package com.deped.protection.validators.xss;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = XSSValidator.class)
@Documented
public @interface XSS {
    String message() default "Your input value is suspicious";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
