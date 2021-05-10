package com.intive.patronative.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class EntityNotFoundException extends RuntimeException {

    protected final FieldError fieldError;

    public EntityNotFoundException(final String fieldName, final String fieldValue, final String message) {
        this.fieldError = new FieldError("String", fieldName, fieldValue, false, null, null, message);
    }

}