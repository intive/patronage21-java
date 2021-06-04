package com.intive.patronative.exception;

import com.intive.patronative.config.LocaleConfig;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = LocaleConfig.getLocaleMessage("userNotFoundMessage");

    public UserNotFoundException(final String fieldName, final String fieldValue) {
        super(fieldName, fieldValue, MESSAGE);
    }

}