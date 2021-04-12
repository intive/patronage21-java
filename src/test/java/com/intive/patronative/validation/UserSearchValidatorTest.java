package com.intive.patronative.validation;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserSearchValidatorTest {

    private final int userSearchDataMinLength = 2;
    private final UserSearchValidator userSearchValidator = new UserSearchValidator(userSearchDataMinLength);

    @ParameterizedTest
    @MethodSource("validSearchData")
    void isValid_shouldReturnFalse(UserSearchDTO userSearchDTO) throws InvalidArgumentException {
        userSearchValidator.validateSearchParameters(userSearchDTO);
        assertDoesNotThrow(() -> userSearchValidator.validateSearchParameters(userSearchDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidSearchData")
    void isValid_shouldReturnTrue(UserSearchDTO userSearchDTO) {
        assertThrows(InvalidArgumentException.class, () -> userSearchValidator.validateSearchParameters(userSearchDTO));
    }

    private static Stream validSearchData() {
        return Stream.of(
                null,
                new UserSearchDTO(null, null, null),
                new UserSearchDTO("Lucas", null, null),
                new UserSearchDTO(null, "Smith", null),
                new UserSearchDTO(null, null, "lSmith"),
                new UserSearchDTO("Lucas", "Smith", null),
                new UserSearchDTO(null, "Smith", "lSmith"),
                new UserSearchDTO("Lucas", null, "lSmith"),
                new UserSearchDTO("Lucas", "Smith", "lSmith"),
                new UserSearchDTO("łukasz", "śmith", "lukaszsmith"),
                new UserSearchDTO("Marta", "dwa-człony", "marta123"),
                new UserSearchDTO("Marta", "dwa człony", "123marta"),
                new UserSearchDTO("Lucas", "Smith", "lSm123ith")
        );
    }

    private static Stream invalidSearchData() {
        return Stream.of(
                new UserSearchDTO("Lucas123", null, null),
                new UserSearchDTO(null, "Smith ", null),
                new UserSearchDTO(null, " Smith", null),
                new UserSearchDTO(null, "Smith123", null),
                new UserSearchDTO(null, "Smith-", null),
                new UserSearchDTO(null, "-Smith", null),
                new UserSearchDTO(null, null, "lSmith*"),
                new UserSearchDTO(null, null, "+123lSmith"),
                new UserSearchDTO(null, null, "+123 lSmith"),
                new UserSearchDTO(null, null, "+123-lSmith"),
                new UserSearchDTO(null, null, "l"),
                new UserSearchDTO(null, "l", null),
                new UserSearchDTO("l", null, null),
                new UserSearchDTO("l", "a", null),
                new UserSearchDTO("l", "a", "s")
        );
    }

}