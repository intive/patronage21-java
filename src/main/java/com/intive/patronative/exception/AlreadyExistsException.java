package com.intive.patronative.exception;

import com.intive.patronative.config.LocaleConfig;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class AlreadyExistsException extends RuntimeException {

    protected final FieldError fieldError;
    private static final String MESSAGE = LocaleConfig.getLocaleMessage("alreadyExistsMessage");

    public AlreadyExistsException(final String fieldName, final String rejectedValue) {
        this.fieldError = new FieldError("String", fieldName, rejectedValue, false, null, null, MESSAGE);
    }

}