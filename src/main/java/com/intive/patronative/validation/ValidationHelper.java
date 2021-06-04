package com.intive.patronative.validation;

import com.intive.patronative.config.LocaleConfig;
import org.springframework.validation.FieldError;

import java.text.MessageFormat;

class ValidationHelper {

    private static final MessageFormat MESSAGE_FORMAT = new MessageFormat("");

    public static FieldError getFieldError(final String fieldName, final String fieldValue, final String message) {
        return new FieldError("String", fieldName, fieldValue, false, null, null, message);
    }

    public static String getMinMaxCharactersMessage(final int minimumCharacters, final int maximumCharacters) {
        final String minimumCharactersMessage = getFormattedMessage(LocaleConfig.getLocaleMessage("validationMinimumCharacterMessage"),
                minimumCharacters);
        final String maximumCharactersMessage = getFormattedMessage(LocaleConfig.getLocaleMessage("validationMaximumCharacterMessage"),
                maximumCharacters);

        return minimumCharactersMessage + ", " + maximumCharactersMessage;
    }

    public static boolean checkLength(final String userData, final int minLength, final int maxLength) {
        return (userData != null) && (minLength <= userData.length()) && (userData.length() <= maxLength);
    }

    public static String getFormattedMessage(final String pattern, final Object... arg) {
        MESSAGE_FORMAT.applyPattern(pattern);

        return MESSAGE_FORMAT.format(arg);
    }

}