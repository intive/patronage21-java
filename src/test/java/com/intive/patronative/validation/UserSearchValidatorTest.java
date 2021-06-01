package com.intive.patronative.validation;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.exception.InvalidArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ContextConfiguration(classes = {UserValidator.class, UserSearchValidator.class})
class UserSearchValidatorTest {

    @Autowired
    private UserSearchValidator userSearchValidator;

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

    private static Stream<UserSearchDTO> validSearchData() {
        return Stream.of(
                null,
                new UserSearchDTO(null, null, null, null, null, null, null),
                new UserSearchDTO(null, null, null, null, null, null, "lucas"),
                new UserSearchDTO(null, null, null, null, null, null, "lucas123"),
                new UserSearchDTO(null, null, null, null, null, null, "Johnnie-walker"),
                new UserSearchDTO(null, null, null, null, null, null, "Luciano Cruz-Coke Carvallo"),
                new UserSearchDTO(null, null, null, null, null, null, "Luciano Cruz-Coke Carvallo"),
                new UserSearchDTO(null, null, null, null, null, null, " Lucas"),
                new UserSearchDTO("Lucas", null, null, null, null, null, null),
                new UserSearchDTO(null, "Smith", null, null, null, null, null),
                new UserSearchDTO(null, null, "lSmith", null, null, null, null),
                new UserSearchDTO(null, null, null, UserRole.CANDIDATE, null, null, null),
                new UserSearchDTO(null, null, null, null, null, "Lucas", null),
                new UserSearchDTO("Lucas", "Smith", null, UserRole.LEADER, null, null, null),
                new UserSearchDTO(null, "Smith", "lSmith", UserRole.LEADER, null, null, null),
                new UserSearchDTO("Lucas", null, "lSmith", null, null, null, null),
                new UserSearchDTO("Lucas", null, "lSmith", null, null, null, null),
                new UserSearchDTO("Lucas", null, "lSmith", null, null, "Lucas", null),
                new UserSearchDTO("Lucas", "Smith", "lSmith", UserRole.valueOf("CANDIDATE"), null, null, null),
                new UserSearchDTO("łukasz", "śmith", "lukaszsmith", UserRole.valueOf("LEADER"), null, null, null),
                new UserSearchDTO("Marta", "dwa-człony", "marta123", UserRole.valueOf("CANDIDATE"), null, null, null),
                new UserSearchDTO("Marta", "dwa człony", "123marta", null, UserStatus.valueOf("ACTIVE"), null, null),
                new UserSearchDTO("Lucas", "Smith", "lSm123ith", UserRole.valueOf("LEADER"), null, null, null),
                new UserSearchDTO(null, null, null, null, null, "Android (mobile)", null),
                new UserSearchDTO(null, null, null, null, null, "Java Script", null),
                new UserSearchDTO(null, null, null, null, null, "Java", null),
                new UserSearchDTO(null, null, null, null, null, "Java5", null),
                new UserSearchDTO(null, null, null, null, null, "amazon-web-services", null)
        );
    }

    private static Stream<UserSearchDTO> invalidSearchData() {
        return Stream.of(
                new UserSearchDTO(null, null, null, null, null, null, ""),
                new UserSearchDTO(null, null, null, null, null, null, "H"),
                new UserSearchDTO(null, null, null, null, null, null, "Bob*the*builder"),
                new UserSearchDTO(null, null, null, null, null, null, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque porta lorem quis tristique tempus. Morbi sit amet dui nulla. Phasellus"),
                new UserSearchDTO("Lucas123", null, null, null, null, null, null),
                new UserSearchDTO(null, " Smith", null, UserRole.valueOf("LEADER"), null, null, null),
                new UserSearchDTO(null, "Smith ", null, UserRole.valueOf("CANDIDATE"), null, null, null),
                new UserSearchDTO(null, "Smith123", null, null, null, null, null),
                new UserSearchDTO(null, "Smith-", null, null, null, null, null),
                new UserSearchDTO(null, "-Smith", null, null, null, null, null),
                new UserSearchDTO(null, null, "lSmith*", null, null, null, null),
                new UserSearchDTO(null, null, "+123lSmith", null, null, null, null),
                new UserSearchDTO(null, null, "+123 lSmith", null, null, null, null),
                new UserSearchDTO(null, null, "+123-lSmith", null, null, null, null),
                new UserSearchDTO(null, null, "l", null, null, null, null),
                new UserSearchDTO(null, "l", null, null, null, null, null),
                new UserSearchDTO("l", null, null, null, null, null, null),
                new UserSearchDTO("l", "a", null, null, null, null, null),
                new UserSearchDTO("l", "a", "s", null, null, null, null),
                new UserSearchDTO(null, null, null, null, null, "Java&", null),
                new UserSearchDTO(null, null, null, null, null, "J", null),
                new UserSearchDTO(null, null, null, null, null, "", null),
                new UserSearchDTO(null, null, null, null, null, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In pharetra", null)
        );
    }

}