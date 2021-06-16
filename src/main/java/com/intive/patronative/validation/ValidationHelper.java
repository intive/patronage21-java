package com.intive.patronative.validation;

import com.intive.patronative.config.LocaleConfig;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;

@Component
class ValidationHelper {

    public FieldError getFieldError(final String fieldName, final String fieldValue, final String message) {
        return new FieldError("String", fieldName, fieldValue, false, null, null, message);
    }

    public String getMinMaxCharactersMessage(final int minimumCharacters, final int maximumCharacters) {
        final var localeMinimumCharactersMessage = LocaleConfig.getLocaleMessage("validationMinimumCharacterMessage");
        final var minimumCharactersMessage = getFormattedMessage(localeMinimumCharactersMessage, minimumCharacters);
        final var localeMaximumCharactersMessage = LocaleConfig.getLocaleMessage("validationMaximumCharacterMessage");
        final var maximumCharactersMessage = getFormattedMessage(localeMaximumCharactersMessage, maximumCharacters);

        return minimumCharactersMessage + ", " + maximumCharactersMessage;
    }

    public boolean checkLength(final String userData, final int minLength, final int maxLength) {
        return (userData != null) && (minLength <= userData.length()) && (userData.length() <= maxLength);
    }

    public String getFormattedMessage(final String pattern, final Object... arg) {
        return new MessageFormat(pattern).format(arg);
    }

}