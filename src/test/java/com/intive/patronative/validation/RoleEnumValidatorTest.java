package com.intive.patronative.validation;

import com.intive.patronative.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleEnumValidatorTest {

    private final RoleEnumValidator validator = new RoleEnumValidator();

    private static final String VALID_ROLE_LEADER = User.Role.LEADER.toString();
    private static final String VALID_ROLE_CANDIDATE = User.Role.CANDIDATE.toString();
    private static final String INVALID_ROLE_TEXT = "foo";

    @Test
    void should_have_correct_role() {
        assertTrue(isValid(VALID_ROLE_LEADER));
        assertTrue(isValid(VALID_ROLE_CANDIDATE));
    }

    @Test
    void should_have_incorrect_role() {
        assertFalse(isValid(INVALID_ROLE_TEXT));
    }

    private boolean isValid(String value) {
        return validator.isValid(value, null);
    }
}
