package com.intive.patronative.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@AllArgsConstructor
public class InvalidArgumentException extends RuntimeException {

    private final List<FieldError> fieldErrors;

}