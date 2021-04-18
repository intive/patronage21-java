package com.intive.patronative.validation;

import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TechnologyGroupsValidator {

    private TechnologyGroupsValidator() {
    }

    private static final int TECHNOLOGY_GROUP_LENGTH = 20;
    private static final int TECHNOLOGY_GROUP_MIN_SIZE = 1;
    private static final int TECHNOLOGY_GROUP_MAX_SIZE = 3;
    private static final String TECHNOLOGY_GROUP_MAX_LENGTH_MESSAGE =
            "Technology group name cannot be empty, up to " + TechnologyGroupsValidator.TECHNOLOGY_GROUP_LENGTH + ".";
    private static final String TECHNOLOGY_GROUP_SIZE_MESSAGE =
            "Acceptable technology groups list size is between " + TechnologyGroupsValidator.TECHNOLOGY_GROUP_MIN_SIZE +
                    " to " + TechnologyGroupsValidator.TECHNOLOGY_GROUP_MAX_SIZE + ".";

    public static void checkTechnologyGroups(final Set<TechnologyGroupDTO> technologyGroups) {
        if (technologyGroups == null) {
            return;
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

    private static List<FieldError> getNamesErrors(final Set<TechnologyGroupDTO> technologyGroups) {
        return technologyGroups.stream()
                .map(technologyGroup -> checkTechnologyGroupName(technologyGroup.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static boolean isTechnologyGroupNameValid(final String technologyGroupName) {
        return (technologyGroupName != null) && (technologyGroupName.length() <= TECHNOLOGY_GROUP_LENGTH);
    }

    private static FieldError checkTechnologyGroupName(final String technologyGroupName) {
        return (isTechnologyGroupNameValid(technologyGroupName)) ?
                null :
                getFieldError(technologyGroupName, TECHNOLOGY_GROUP_MAX_LENGTH_MESSAGE);
    }

    private static FieldError checkTechnologyGroupsSize(final Set<TechnologyGroupDTO> technologyGroups) {
        final var size = technologyGroups.size();
        return (size < TECHNOLOGY_GROUP_MIN_SIZE || size > TECHNOLOGY_GROUP_MAX_SIZE) ?
                getFieldError("current size: " + size, TECHNOLOGY_GROUP_SIZE_MESSAGE) :
                null;
    }

    private static FieldError getFieldError(final String fieldValue, final String message) {
        return new FieldError("String", "technologyGroups", fieldValue, false, null, null, message);
    }
}