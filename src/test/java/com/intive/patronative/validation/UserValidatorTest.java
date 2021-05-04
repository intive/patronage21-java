package com.intive.patronative.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserValidatorTest {

    @ParameterizedTest
    @MethodSource("validFirstNames")
    void isFirstNameValid_shouldReturnTrue(String firstName) {
        assertTrue(UserValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("invalidFirstNames")
    void isFirstNameValid_shouldReturnFalse(String firstName) {
        assertFalse(UserValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("validLastNames")
    void isLastNameValid_shouldReturnTrue(String lastName) {
        assertTrue(UserValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("invalidLastNames")
    void isLastNameValid_shouldReturnFalse(String lastName) {
        assertFalse(UserValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("validUsernames")
    void isUsernameValid_shouldReturnTrue(String username) {
        assertTrue(UserValidator.isUsernameValid(username));
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void isUsernameValid_shouldReturnFalse(String username) {
        assertFalse(UserValidator.isUsernameValid(username));
    }

    private static Stream validFirstNames() {
        return Stream.of("lucas", "Lucas", "łukasz", "Łukasz", "MatEuSZ", "Arabella", "O");
    }

    private static Stream invalidFirstNames() {
        return Stream.of("lucas ", " Lucas", "łuk asz", "Łuk-asz", "MatE.uSZ", "*Marek", "Szym0n", "{Sometext}", "-Mark",
                "/Henk", "#home", "");
    }

    private static Stream validLastNames() {
        return Stream.of("Zukerberg", "Jobs", "Gąsienica-Makowski", "Gąsienica Makowski", "Gąsior", "markowski", "Curuś");
    }

    private static Stream invalidLastNames() {
        return Stream.of("Zuke*rberg", "J+obs", "Gąsienica-", "-Gąsienica-", "-Gąsienica", "Gąsienica ", " Gąsienica ",
                " Gąsienica", "Gąsi$r", "markowsk@", "Curu1", "Cu- ru", "");
    }

    private static Stream validUsernames() {
        return Stream.of("lucas", "lucas123", "123lucas", "luc123as", "Lucas", "nameSurname");
    }

    private static Stream invalidUsernames() {
        return Stream.of("Luca-", "Hi Mark", "123-patrick", "Bob*the*builder", "-masha", "the+Bear");
    }

}