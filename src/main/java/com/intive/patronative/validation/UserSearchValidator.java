package com.intive.patronative.validation;

import com.intive.patronative.config.LocaleConfig;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UserSearchValidator {

    private static final int MAX_OTHER_LENGTH = 162;
    private static final int MAX_TECHNOLOGY_GROUP_NAME_LENGTH = 32;
    private static final Matcher OTHER_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż0-9 -]+$").matcher("");
    private static final Matcher TECHNOLOGY_GROUP_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż0-9() -]+$").matcher("");

    private final UserValidator userValidator;

    @Value("${validators.search.data.length.min}")
    private int minSearchDataLength;
    private int maxOtherLength;

    @PostConstruct
    private void setMaxOtherLength() {
        final var lengthOfTwoSpaces = 2;

        this.maxOtherLength = lengthOfTwoSpaces + userValidator.getMaxLoginLength() + userValidator.getMaxFirstNameLength() +
                userValidator.getMaxLastNameLength();
    }

    public void validateSearchParameters(final UserSearchDTO userSearchDTO) throws InvalidArgumentException {
        final var fieldErrors = getFieldErrors(userSearchDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    private List<FieldError> getFieldErrors(final UserSearchDTO userSearchDTO) {
        return Optional.ofNullable(userSearchDTO)
                .map(user -> Stream
                        .of(checkLogin(user.getLogin()),
                                checkFirstName(user.getFirstName()),
                                checkLastName(user.getLastName()),
                                checkOther(user.getOther()),
                                checkTechnologyGroup(user.getTechnologyGroup()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private FieldError checkLogin(final String login) {
        final var loginMessage = ValidationHelper.getFormattedMessage(LocaleConfig.getLocaleMessage("validationLoginMessage"),
                ValidationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxLoginLength()));

        return ((login == null) || (ValidationHelper.checkLength(login, minSearchDataLength, userValidator.getMaxLoginLength())
                && userValidator.isLoginValid(login)))
                ? null
                : ValidationHelper.getFieldError("login", login, loginMessage);
    }

    private FieldError checkFirstName(final String firstName) {
        final var firstNameMessage = ValidationHelper.getFormattedMessage(LocaleConfig.getLocaleMessage("validationFirstNameMessage"),
                ValidationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxFirstNameLength()));

        return ((firstName == null) || (ValidationHelper.checkLength(firstName, minSearchDataLength, userValidator.getMaxFirstNameLength())
                && userValidator.isFirstNameValid(firstName)))
                ? null
                : ValidationHelper.getFieldError("firstName", firstName, firstNameMessage);
    }

    private FieldError checkLastName(final String lastName) {
        final var lastNameMessage = ValidationHelper.getFormattedMessage(LocaleConfig.getLocaleMessage("validationLastNameMessage"),
                ValidationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxLastNameLength()));

        return ((lastName == null) || (ValidationHelper.checkLength(lastName, minSearchDataLength, userValidator.getMaxLastNameLength())
                && userValidator.isLastNameValid(lastName)))
                ? null
                : ValidationHelper.getFieldError("lastName", lastName, lastNameMessage);
    }

    private FieldError checkOther(final String other) {
        final var otherMessage = ValidationHelper.getFormattedMessage(LocaleConfig.getLocaleMessage("validationOtherMessage"),
                ValidationHelper.getMinMaxCharactersMessage(minSearchDataLength, maxOtherLength));

        return ((other == null) || (ValidationHelper.checkLength(other, minSearchDataLength, maxOtherLength)
                && isOtherValid(other)))
                ? null
                : ValidationHelper.getFieldError("other", other, otherMessage);
    }

    private boolean isOtherValid(final String other) {
        return (other != null) && (other.length() <= MAX_OTHER_LENGTH) && OTHER_MATCHER.reset(other).matches();
    }

    private FieldError checkTechnologyGroup(final String technologyGroup) {
        final var technologyGroupMessage = ValidationHelper.getFormattedMessage(LocaleConfig.getLocaleMessage("validationTechnologyGroupMessage"),
                ValidationHelper.getMinMaxCharactersMessage(minSearchDataLength, MAX_TECHNOLOGY_GROUP_NAME_LENGTH));

        return ((technologyGroup == null) || (ValidationHelper.checkLength(technologyGroup, minSearchDataLength, MAX_TECHNOLOGY_GROUP_NAME_LENGTH)
                && isTechnologyGroupValid(technologyGroup)))
                ? null
                : ValidationHelper.getFieldError("technologyGroup", technologyGroup, technologyGroupMessage);
    }

    private boolean isTechnologyGroupValid(final String technologyGroup) {
        return (technologyGroup != null) && TECHNOLOGY_GROUP_MATCHER.reset(technologyGroup).matches();
    }

}