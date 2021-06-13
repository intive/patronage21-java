package com.intive.patronative.exception;

import com.intive.patronative.config.LocaleConfig;
import lombok.Getter;

@Getter
public class StatusNotFoundException extends EntityNotFoundException {

    private static final String MESSAGE = LocaleConfig.getLocaleMessage("statusNotFoundMessage");
    private static final String FIELD_NAME = "status";

    public StatusNotFoundException(final String fieldValue) {
        super(FIELD_NAME, fieldValue, MESSAGE);
    }

}