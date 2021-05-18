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

    public void validateSearchParameters(final UserSearchDTO userSearchDTO) throws InvalidArgumentException {
        final var fieldErrors = getFieldErrors(userSearchDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    private Optional<FieldError> checkFirstName(final String firstName) {
        final var firstNameMessage = "Letters only, minimum " + userSearchDataMinLength + " letters";
        return (firstName != null) && (!UserValidator.isFirstNameValid(firstName) || isLengthInvalid(firstName)) ?
                Optional.of(new FieldError("String", "firstName", firstName, false, null, null, firstNameMessage)) :
                Optional.empty();
    }

    private Optional<FieldError> checkLastName(final String lastName) {
        final var lastNameMessage = "Letters only, minimum " + userSearchDataMinLength +
                " letters, dash/space allowed in case of two-part surname";
        return (lastName != null) && (!UserValidator.isLastNameValid(lastName) || isLengthInvalid(lastName)) ?
                Optional.of(new FieldError("String", "lastName", lastName, false, null, null, lastNameMessage)) :
                Optional.empty();
    }

    private Optional<FieldError> checkUsername(final String username) {
        final var usernameMessage = "Letters or numbers, minimum " + userSearchDataMinLength + " letters";
        return (username != null) && (!UserValidator.isLoginValid(username) || isLengthInvalid(username)) ?
                Optional.of(new FieldError("String", "username", username, false, null, null, usernameMessage)) :
                Optional.empty();
    }

    private List<FieldError> getFieldErrors(final UserSearchDTO userSearchDTO) {
        if (userSearchDTO != null) {
            return Stream.of(checkFirstName(userSearchDTO.getFirstName()),
                    checkLastName(userSearchDTO.getLastName()),
                    checkUsername(userSearchDTO.getUsername()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private boolean isLengthInvalid(final String userSearchData) {
        return ((userSearchData != null) && (userSearchData.length() < userSearchDataMinLength));
    }

}