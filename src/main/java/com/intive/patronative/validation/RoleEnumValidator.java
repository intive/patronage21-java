package com.intive.patronative.validation;


import com.intive.patronative.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class RoleEnumValidator implements ConstraintValidator<RoleEnum, String> {

    @Override
    public void initialize(RoleEnum annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(User.Role.values())
                .anyMatch(enumValue -> enumValue.toString().equalsIgnoreCase(value));
    }
}
