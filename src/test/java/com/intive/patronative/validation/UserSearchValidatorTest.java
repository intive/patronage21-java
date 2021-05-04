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
    private final UserSearchValidator userSearchValidator = new UserSearchValidator(userSearchDataMinLength);

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
                new UserSearchDTO(null, null, null, null),
                new UserSearchDTO("Lucas", null, null, null),
                new UserSearchDTO(null, "Smith", null, null),
                new UserSearchDTO(null, null, "lSmith", null),
                new UserSearchDTO(null, null, null, UserRole.CANDIDATE),
                new UserSearchDTO("Lucas", "Smith", null, UserRole.LEADER),
                new UserSearchDTO(null, "Smith", "lSmith", UserRole.LEADER),
                new UserSearchDTO("Lucas", null, "lSmith", null),
                new UserSearchDTO("Lucas", "Smith", "lSmith", UserRole.valueOf("CANDIDATE")),
                new UserSearchDTO("łukasz", "śmith", "lukaszsmith", UserRole.valueOf("LEADER")),
                new UserSearchDTO("Marta", "dwa-człony", "marta123", UserRole.valueOf("CANDIDATE")),
                new UserSearchDTO("Marta", "dwa człony", "123marta", null),
                new UserSearchDTO("Lucas", "Smith", "lSm123ith", UserRole.valueOf("LEADER"))
        );
    }

    private static Stream invalidSearchData() {
        return Stream.of(
                new UserSearchDTO("Lucas123", null, null, null),
                new UserSearchDTO(null, " Smith", null, UserRole.valueOf("LEADER")),
                new UserSearchDTO(null, "Smith ", null, UserRole.valueOf("CANDIDATE")),
                new UserSearchDTO(null, "Smith123", null, null),
                new UserSearchDTO(null, "Smith-", null, null),
                new UserSearchDTO(null, "-Smith", null, null),
                new UserSearchDTO(null, null, "lSmith*", null),
                new UserSearchDTO(null, null, "+123lSmith", null),
                new UserSearchDTO(null, null, "+123 lSmith", null),
                new UserSearchDTO(null, null, "+123-lSmith", null),
                new UserSearchDTO(null, null, "l", null),
                new UserSearchDTO(null, "l", null, null),
                new UserSearchDTO("l", null, null, null),
                new UserSearchDTO("l", "a", null, null),
                new UserSearchDTO("l", "a", "s", null)
        );
    }

}