package com.intive.patronative.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public AlreadyExistsException(final String fieldName, final String rejectedValue) {
        final var message = "Already exists.";
        this.fieldErrors = List.of(new FieldError("String", fieldName, rejectedValue, false, null, null, message));
    }
}
