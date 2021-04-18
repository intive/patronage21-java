package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class StatusNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "Status not found";
    private static final String FIELD_NAME = "status";

    public StatusNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }
}