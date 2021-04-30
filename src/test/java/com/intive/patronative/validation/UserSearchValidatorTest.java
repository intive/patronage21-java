package com.intive.patronative.validation;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.exception.InvalidArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserSearchValidatorTest {

    private final int userSearchDataMinLength = 2;
    private final int userSearchDataMaxLength = 125;
    private final UserSearchValidator userSearchValidator = new UserSearchValidator(userSearchDataMinLength, userSearchDataMaxLength);

    @ParameterizedTest
    @MethodSource("validSearchData")
    void validateSearchParameters_throwsNothing(final UserSearchDTO userSearchDTO) throws InvalidArgumentException {
        userSearchValidator.validateSearchParameters(userSearchDTO);
        assertDoesNotThrow(() -> userSearchValidator.validateSearchParameters(userSearchDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidSearchData")
    void validateSearchParameters_throwsInvalidArgumentException(final UserSearchDTO userSearchDTO) {
        assertThrows(InvalidArgumentException.class, () -> userSearchValidator.validateSearchParameters(userSearchDTO));
    }

    private static Stream validSearchData() {
        return Stream.of(
                null,
                new UserSearchDTO(null, null, null, null, null, null),
                new UserSearchDTO("Lucas", null, null, null, null, null),
                new UserSearchDTO(null, "Smith", null, null, null, null),
                new UserSearchDTO(null, null, "lSmith", null, null, null),
                new UserSearchDTO(null, null, null, UserRole.CANDIDATE, null, null),
                new UserSearchDTO(null, null, null, null, "Java", null),
                new UserSearchDTO(null, null, null, null, null, "Lucas"),
                new UserSearchDTO("Lucas", "Smith", null, UserRole.LEADER, null, null),
                new UserSearchDTO(null, "Smith", "lSmith", UserRole.LEADER, null, null),
                new UserSearchDTO("Lucas", null, "lSmith", null, null, null),
                new UserSearchDTO("Lucas", null, "lSmith", null, "Java", null),
                new UserSearchDTO("Lucas", null, "lSmith", null, null, "Lucas"),
                new UserSearchDTO("Lucas", "Smith", "lSmith", UserRole.valueOf("CANDIDATE"), null, null),
                new UserSearchDTO("łukasz", "śmith", "lukaszsmith", UserRole.valueOf("LEADER"), null, null),
                new UserSearchDTO("Marta", "dwa-człony", "marta123", UserRole.valueOf("CANDIDATE"), null, null),
                new UserSearchDTO("Marta", "dwa człony", "123marta", null, null, null),
                new UserSearchDTO("Lucas", "Smith", "lSm123ith", UserRole.valueOf("LEADER"), null, null),
                new UserSearchDTO(null, null, null, null, "Android (mobile)", null),
                new UserSearchDTO(null, null, null, null, "Java Script", null),
                new UserSearchDTO(null, null, null, null, "Java", null),
                new UserSearchDTO(null, null, null, null, "Java5", null),
                new UserSearchDTO(null, null, null, null, "amazon-web-services", null)
        );
    }

    private static Stream invalidSearchData() {
        return Stream.of(
                new UserSearchDTO("Lucas123", null, null, null, null, null),
                new UserSearchDTO(null, " Smith", null, UserRole.valueOf("LEADER"), null, null),
                new UserSearchDTO(null, "Smith ", null, UserRole.valueOf("CANDIDATE"), null, null),
                new UserSearchDTO(null, "Smith123", null, null, null, null),
                new UserSearchDTO(null, "Smith-", null, null, null, null),
                new UserSearchDTO(null, "-Smith", null, null, null, null),
                new UserSearchDTO(null, null, "lSmith*", null, null, null),
                new UserSearchDTO(null, null, "+123lSmith", null, null, null),
                new UserSearchDTO(null, null, "+123 lSmith", null, null, null),
                new UserSearchDTO(null, null, "+123-lSmith", null, null, null),
                new UserSearchDTO(null, null, "l", null, null, null),
                new UserSearchDTO(null, "l", null, null, null, null),
                new UserSearchDTO("l", null, null, null, null, null),
                new UserSearchDTO("l", "a", null, null, null, null),
                new UserSearchDTO("l", "a", "s", null, null, null),
                new UserSearchDTO(null, null, null, null, "Java&", null),
                new UserSearchDTO(null, null, null, null, "J", null),
                new UserSearchDTO(null, null, null, null, "", null),
                new UserSearchDTO(null, null, null, null, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In pharetra", null)
        );
    }

}