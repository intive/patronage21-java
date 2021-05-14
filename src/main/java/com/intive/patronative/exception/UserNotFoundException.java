package com.intive.patronative.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final FieldError fieldError;
    private static final String MESSAGE = "User not found";

    public UserNotFoundException(final String fieldName, final String fieldValue) {
        this.fieldError = new FieldError("String", fieldName, fieldValue, false, null, null, MESSAGE);
    }

}