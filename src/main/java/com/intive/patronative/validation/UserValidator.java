package com.intive.patronative.validation;

import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern firstNamePattern = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+$");
    private static final Pattern lastNamePattern = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+[- ]?[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+$");
    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean isFirstNameValid(final String firstName) {
        return firstNamePattern.matcher(firstName).matches();
    }

    public static boolean isLastNameValid(final String lastName) {
        return lastNamePattern.matcher(lastName).matches();
    }

    public static boolean isUsernameValid(final String username) {
        return usernamePattern.matcher(username).matches();
    }

}