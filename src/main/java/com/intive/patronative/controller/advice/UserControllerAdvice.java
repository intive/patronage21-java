package com.intive.patronative.controller.advice;

import com.intive.patronative.exception.InvalidArgumentException;
import org.springframework.http.HttpStatus;
import lombok.Value;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidArgumentException.class)
    public ValidationErrorResponse invalidArgumentHandler(final InvalidArgumentException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @Value
    private class ValidationErrorResponse {
        List<ViolationError> violationErrors;
    }

    @Value
    private class ViolationError {
        String fieldName;
        Object rejectedValue;
        String message;
    }

    private ValidationErrorResponse buildErrorResponse(final List<FieldError> fieldErrors) {
        return new ValidationErrorResponse(fieldErrors.stream().map(fieldError -> new ViolationError(fieldError.getField(),
                fieldError.getRejectedValue(), fieldError.getDefaultMessage())).collect(Collectors.toList()));
    }

}