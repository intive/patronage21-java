package com.intive.patronative.exception;

import lombok.Getter;

@Getter
public class RoleNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = "Role not found";
    private static final String FIELD_NAME = "role";

    public RoleNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }
}