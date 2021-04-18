package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class TechnologyNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "Technology not found";
    private static final String FIELD_NAME = "technology";

    public TechnologyNotFoundException(final String fieldName, final String fieldValue, final String message) {
        super(fieldName, fieldValue, message);
    }
}