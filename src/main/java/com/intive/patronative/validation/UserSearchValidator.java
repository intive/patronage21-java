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
    private final ValidationHelper validationHelper;

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
        final var localeMessage = LocaleConfig.getLocaleMessage("validationLoginMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxLoginLength());
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return ((login == null) || (validationHelper.checkLength(login, minSearchDataLength, userValidator.getMaxLoginLength())
                && userValidator.isLoginValid(login)))
                ? null
                : validationHelper.getFieldError("login", login, message);
    }

    private FieldError checkFirstName(final String firstName) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationFirstNameMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxFirstNameLength());
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return ((firstName == null) || (validationHelper.checkLength(firstName, minSearchDataLength, userValidator.getMaxFirstNameLength())
                && userValidator.isFirstNameValid(firstName)))
                ? null
                : validationHelper.getFieldError("firstName", firstName, message);
    }

    private FieldError checkLastName(final String lastName) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationLastNameMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minSearchDataLength, userValidator.getMaxLastNameLength());
        final var message = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return ((lastName == null) || (validationHelper.checkLength(lastName, minSearchDataLength, userValidator.getMaxLastNameLength())
                && userValidator.isLastNameValid(lastName)))
                ? null
                : validationHelper.getFieldError("lastName", lastName, message);
    }

    private FieldError checkOther(final String other) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationOtherMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minSearchDataLength, maxOtherLength);
        final var otherMessage = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return ((other == null) || (validationHelper.checkLength(other, minSearchDataLength, maxOtherLength)
                && isOtherValid(other)))
                ? null
                : validationHelper.getFieldError("other", other, otherMessage);
    }

    private boolean isOtherValid(final String other) {
        return (other != null) && (other.length() <= MAX_OTHER_LENGTH) && OTHER_MATCHER.reset(other).matches();
    }

    private FieldError checkTechnologyGroup(final String technologyGroup) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationTechnologyGroupMessage");
        final var charactersMessage = validationHelper.getMinMaxCharactersMessage(minSearchDataLength, MAX_TECHNOLOGY_GROUP_NAME_LENGTH);
        final var technologyGroupMessage = validationHelper.getFormattedMessage(localeMessage, charactersMessage);

        return ((technologyGroup == null) || (validationHelper.checkLength(technologyGroup, minSearchDataLength, MAX_TECHNOLOGY_GROUP_NAME_LENGTH)
                && isTechnologyGroupValid(technologyGroup)))
                ? null
                : validationHelper.getFieldError("technologyGroup", technologyGroup, technologyGroupMessage);
    }

    private boolean isTechnologyGroupValid(final String technologyGroup) {
        return (technologyGroup != null) && TECHNOLOGY_GROUP_MATCHER.reset(technologyGroup).matches();
    }

}