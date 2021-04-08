package com.intive.patronative.validation;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UserControllerValidationHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException validationException){
        final var error = new ValidationErrorResponse();
        for (final var fieldError : validationException.getBindingResult().getFieldErrors()) {
            error.getViolationErrors().add(new ViolationError(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    @Value
    private class ValidationErrorResponse {
        List<ViolationError> violationErrors = new ArrayList<>();

    }

    @Value
    private class ViolationError {
        String fieldName;
        Object rejectedValue;
        String message;
    }
}