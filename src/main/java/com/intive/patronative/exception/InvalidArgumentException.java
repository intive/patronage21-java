package com.intive.patronative.exception;

import com.intive.patronative.config.LocaleConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class InvalidArgumentException extends RuntimeException {

    private final List<FieldError> fieldErrors;
    private static final String DEFAULT_MESSAGE = LocaleConfig.getLocaleMessage("invalidArgumentMessage");

    public InvalidArgumentException(final String fieldName, final String fieldValue) {
        this.fieldErrors = Collections.singletonList(new FieldError("String", fieldName, fieldValue, false, null, null, DEFAULT_MESSAGE));
    }

}