package com.intive.patronative.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class AlreadyExistsException extends RuntimeException {

    protected final FieldError fieldError;
    private static final String MESSAGE = "Field with provided name already exists";

    public AlreadyExistsException(final String fieldName, final String rejectedValue) {
        this.fieldError = new FieldError("String", fieldName, rejectedValue, false, null, null, MESSAGE);
    }
}