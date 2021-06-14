package com.intive.patronative.exception;

import com.intive.patronative.config.LocaleConfig;
import lombok.Getter;

@Getter
public class UserNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = LocaleConfig.getLocaleMessage("userNotFoundMessage");
    private static final String FIELD_NAME = "login";

    public UserNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }

}