package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class GenderNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "Gender not found";
    private static final String FIELD_NAME = "gender";

    public GenderNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }
}