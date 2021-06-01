package com.intive.patronative.validation;

import org.springframework.validation.FieldError;

class ValidationHelper {

    public static FieldError getFieldError(final String fieldName, final String fieldValue, final String message) {
        return new FieldError("String", fieldName, fieldValue, false, null, null, message);
    }

    public static String getMinMaxCharactersMessage(final int minimumCharacters, final int maximumCharacters) {
        return "minimum " + minimumCharacters + " characters, maximum " + maximumCharacters + " characters";
    }

    public static boolean checkLength(final String userData, final int minLength, final int maxLength) {
        return (userData != null) && (minLength <= userData.length()) && (userData.length() <= maxLength);
    }

}