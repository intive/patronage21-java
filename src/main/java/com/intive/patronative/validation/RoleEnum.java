package com.intive.patronative.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { RoleEnumValidator.class })
public @interface RoleEnum {
    String message() default "Invalid role value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
