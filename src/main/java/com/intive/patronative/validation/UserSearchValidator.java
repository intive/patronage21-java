package com.intive.patronative.validation;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
@AllArgsConstructor
@PropertySource("classpath:application.yml")
public class UserSearchValidator {

    @Value("${validators.search.user.data.length.min}")
    private int userSearchDataMinLength;

    @Value("${validators.search.user.data.length.max}")
    private int userSearchDataMaxLength;

    public void validateSearchParameters(final UserSearchDTO userSearchDTO) {
        final var fieldErrors = getFieldErrors(userSearchDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    private Optional<FieldError> checkFirstName(final String firstName) {
        final var firstNameMessage = "Letters only, " + getMinMaxCharactersMessage();
        return (firstName == null) || (UserValidator.isFirstNameValid(firstName) && isLengthValid(firstName))
                ? Optional.empty()
                : Optional.of(new FieldError("String", "firstName", firstName, false, null, null, firstNameMessage));
    }

    private Optional<FieldError> checkLastName(final String lastName) {
        final var lastNameMessage = "Letters only, dash/space allowed in case of two-part surname, " +
                getMinMaxCharactersMessage();
        return (lastName == null) || (UserValidator.isLastNameValid(lastName) && isLengthValid(lastName))
                ? Optional.empty()
                : Optional.of(new FieldError("String", "lastName", lastName, false, null, null, lastNameMessage));
    }

    private Optional<FieldError> checkLogin(final String login) {
        final var loginMessage = "Letters or numbers, " + getMinMaxCharactersMessage();
        return (login == null) || (UserValidator.isLoginValid(login) && isLengthValid(login))
                ? Optional.empty()
                : Optional.of(new FieldError("String", "login", login, false, null, null, loginMessage));
    }

    private Optional<FieldError> checkOther(final String other) {
        final var otherMessage = "Letters, spaces, numbers and dashes allowed, " + getMinMaxCharactersMessage();
        return (other == null) || (UserValidator.isOtherValid(other) && isLengthValid(other))
                ? Optional.empty()
                : Optional.of(new FieldError("String", "other", other, false, null, null, otherMessage));
    }

    private Optional<FieldError> checkTechnologyGroup(final String technologyGroup) {
        final var technologyGroupMessage = "Letters or numbers, dash/space/parentheses allowed, " +
                getMinMaxCharactersMessage();
        return (technologyGroup == null) || (UserValidator.isTechnologyGroupValid(technologyGroup) && isLengthValid(technologyGroup))
                ? Optional.empty()
                : Optional.of(new FieldError("String", "technologyGroup", technologyGroup, false, null, null, technologyGroupMessage));
    }

    private String getMinMaxCharactersMessage() {
        return "minimum " + userSearchDataMinLength + " characters, maximum " + userSearchDataMaxLength + " characters";
    }

    private List<FieldError> getFieldErrors(final UserSearchDTO userSearchDTO) {
        if (userSearchDTO != null) {
            return Stream.of(checkFirstName(userSearchDTO.getFirstName()),
                    checkLastName(userSearchDTO.getLastName()),
                    checkLogin(userSearchDTO.getLogin()),
                    checkOther(userSearchDTO.getOther()),
                    checkTechnologyGroup(userSearchDTO.getTechnologyGroup()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private boolean isLengthValid(final String userSearchData) {
        return (userSearchData != null) &&
                ((userSearchData.length() >= userSearchDataMinLength) &&
                        (userSearchData.length() <= userSearchDataMaxLength));
    }
}