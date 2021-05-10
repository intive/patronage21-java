package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "User not found";

    public UserNotFoundException(final String fieldName, final String fieldValue) {
        super(fieldName, fieldValue, MESSAGE);
    }

}