package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class TechnologyGroupNotFoundException extends TechnologyNotFoundException {

    private static final String MESSAGE = "Technology group not found";
    private static final String FIELD_NAME = "technologyGroup";

    public TechnologyGroupNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }
}