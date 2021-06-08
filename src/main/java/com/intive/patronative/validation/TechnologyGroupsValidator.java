package com.intive.patronative.validation;

import com.intive.patronative.config.LocaleConfig;
import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class TechnologyGroupsValidator {

    private static final int TECHNOLOGY_GROUP_MAX_NAME_LENGTH = 32;
    private static final String TECHNOLOGY_GROUP_EMPTY_MESSAGE = LocaleConfig.getLocaleMessage("validationEmptyTechnologyGroupMessage");

    private final ValidationHelper validationHelper;

    @Value("${validators.user.technology-groups.minimum-participation}")
    private int technologyGroupMinNumberOfParticipation;

    @Value("${validators.user.technology-groups.maximum-participation}")
    private int technologyGroupMaxNumberOfParticipation;

    public void checkTechnologyGroups(final Set<TechnologyGroupDTO> technologyGroups) {
        if (technologyGroups == null) {
            throw new InvalidArgumentException(List.of(getFieldError(null, TECHNOLOGY_GROUP_EMPTY_MESSAGE)));
        }
        final var errors = Stream.concat(
                getNamesErrors(technologyGroups).stream(),
                Stream.of(checkTechnologyGroupsSize(technologyGroups)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new InvalidArgumentException(errors);
        }
    }

    private List<FieldError> getNamesErrors(final Set<TechnologyGroupDTO> technologyGroups) {
        return technologyGroups.stream()
                .map(technologyGroup -> checkTechnologyGroupName(technologyGroup.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean isTechnologyGroupNameValid(final String technologyGroupName) {
        return (technologyGroupName != null) && (technologyGroupName.length() <= TECHNOLOGY_GROUP_MAX_NAME_LENGTH);
    }

    private FieldError checkTechnologyGroupName(final String technologyGroupName) {
        final var localeMessage = LocaleConfig.getLocaleMessage("validationTechnologyGroupMaxNameLengthMessage");
        final var message = validationHelper.getFormattedMessage(localeMessage, TECHNOLOGY_GROUP_MAX_NAME_LENGTH);

        return (isTechnologyGroupNameValid(technologyGroupName))
                ? null
                : getFieldError(technologyGroupName, message);
    }

    private FieldError checkTechnologyGroupsSize(final Set<TechnologyGroupDTO> technologyGroups) {
        final var size = technologyGroups.size();
        final var localeMessage = LocaleConfig.getLocaleMessage("validationTechnologyGroupParticipationMessage");
        final var message = validationHelper.getFormattedMessage(localeMessage, technologyGroupMinNumberOfParticipation,
                technologyGroupMaxNumberOfParticipation);

        return (size >= technologyGroupMinNumberOfParticipation && size <= technologyGroupMaxNumberOfParticipation)
                ? null
                : getFieldError("current size: " + size, message);
    }

    private FieldError getFieldError(final String fieldValue, final String message) {
        return new FieldError("String", "technologyGroups", fieldValue, false, null, null, message);
    }

}