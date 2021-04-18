package com.intive.patronative.controller.advice;

import com.intive.patronative.exception.AlreadyExistsException;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ValidationErrorResponse methodArgumentTypeMismatchHandler(final MethodArgumentTypeMismatchException exception) {
        return buildErrorResponse(new ViolationError(exception.getName(), exception.getValue(), exception.getCause().getMessage()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidArgumentException.class)
    public ValidationErrorResponse invalidArgumentHandler(final InvalidArgumentException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    public ValidationErrorResponse alreadyExistException(final AlreadyExistsException exception) {
        return buildErrorResponse(exception.getFieldErrors());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ValidationErrorResponse userNotFoundHandler(final UserNotFoundException exception) {
        return buildErrorResponse(Collections.singletonList(exception.getFieldError()));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class ValidationErrorResponse {
        private List<ViolationError> violationErrors;
    }

    @Value
    private class ViolationError {
        String fieldName;
        Object rejectedValue;
        String message;
    }

    private ValidationErrorResponse buildErrorResponse(final List<FieldError> fieldErrors) {
        return (fieldErrors == null)
                ? new ValidationErrorResponse()
                : new ValidationErrorResponse(fieldErrors.stream().map(fieldError -> new ViolationError(fieldError.getField(),
                fieldError.getRejectedValue(), fieldError.getDefaultMessage())).collect(Collectors.toList()));
    }

    private ValidationErrorResponse buildErrorResponse(final ViolationError violationError) {
        return new ValidationErrorResponse(Collections.singletonList(violationError));
    }

}