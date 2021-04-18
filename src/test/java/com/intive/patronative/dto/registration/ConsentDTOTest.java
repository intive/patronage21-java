package com.intive.patronative.dto.registration;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConsentDTOTest {

    @Test
    void consentsAreEquals() {
        final var consent = ConsentDTO.builder()
                .text("foo")
                .consentId(BigDecimal.valueOf(1))
                .flagConfirmed(true)
                .build();
        final var consent2 = ConsentDTO.builder()
                .text("foo2")
                .consentId(BigDecimal.valueOf(1))
                .flagConfirmed(false)
                .build();

        assertEquals(consent, consent2);
    }

    @Test
    void consentsAreNotEquals() {
        final var consent = ConsentDTO.builder()
                .text("foo")
                .consentId(BigDecimal.valueOf(1))
                .flagConfirmed(true)
                .build();
        final var consent2 = ConsentDTO.builder()
                .text("foo")
                .consentId(BigDecimal.valueOf(2))
                .flagConfirmed(true)
                .build();

        assertNotEquals(consent, consent2);
    }
}