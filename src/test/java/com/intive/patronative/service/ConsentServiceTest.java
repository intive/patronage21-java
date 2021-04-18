package com.intive.patronative.service;

import com.intive.patronative.dto.registration.ConsentDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.mapper.ConsentMapper;
import com.intive.patronative.repository.ConsentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

import static com.intive.patronative.service.ConsentProviderTestData.getFirstConsentDTO;
import static com.intive.patronative.service.ConsentProviderTestData.getFirstConsentEntity;
import static com.intive.patronative.service.ConsentProviderTestData.getSecondConsentDTO;
import static com.intive.patronative.service.ConsentProviderTestData.getSecondConsentEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ConsentServiceTest {

    @Mock
    private ConsentRepository consentRepository;

    private ConsentService consentService;

    @BeforeEach
    void setUp() {
        final var consentMapper = new ConsentMapper();
        consentService = new ConsentService(consentRepository, consentMapper);
    }

    @Test
    void oneRequiredAndConfirmed_shouldReturnOne() {
        final var consent = getFirstConsentEntity(true);
        final var consent2 = getSecondConsentEntity(false);
        final var consentDTO = getFirstConsentDTO(true);
        final var consentDTO2 = getSecondConsentDTO(false);

        when(consentRepository.findAllByRequired(true)).thenReturn(Set.of(consent));
        when(consentRepository.findAllByRequired(false)).thenReturn(Set.of(consent2));

        final var result =
                consentService.getValidatedAndConfirmedConsents(Set.of(consentDTO, consentDTO2));

        assertEquals(1, result.size());
    }

    @Test
    void bothConsentsConfirmedAndExists_shouldReturnTwo() {
        final var consent = getFirstConsentEntity(true);
        final var consent2 = getSecondConsentEntity(false);
        final var consentDTO = getFirstConsentDTO(true);
        final var consentDTO2 = getSecondConsentDTO(true);

        when(consentRepository.findAllByRequired(true)).thenReturn(Set.of(consent));
        when(consentRepository.findAllByRequired(false)).thenReturn(Set.of(consent2));

        final var result =
                consentService.getValidatedAndConfirmedConsents(Set.of(consentDTO, consentDTO2));

        assertEquals(2, result.size());
    }

    @Test
    void requiredItsNotConfirmed_shouldThrowException() {
        final var consent = getFirstConsentEntity(true);
        final var consent2 = getSecondConsentEntity(false);
        final var consentDTO = getFirstConsentDTO(false);
        final var consentDTO2 = getSecondConsentDTO(false);

        when(consentRepository.findAllByRequired(true)).thenReturn(Set.of(consent));
        when(consentRepository.findAllByRequired(false)).thenReturn(Set.of(consent2));

        assertThrows(InvalidArgumentException.class,
                () -> consentService.getValidatedAndConfirmedConsents(Set.of(consentDTO, consentDTO2)));
    }

    @Test
    void consentNotFound_shouldThrowException() {
        final var nonExistingInDbConsent = new ConsentDTO("foo", BigDecimal.valueOf(3), true);

        when(consentRepository.findAllByRequired(true)).thenReturn(Collections.emptySet());
        when(consentRepository.findAllByRequired(false)).thenReturn(Collections.emptySet());

        assertThrows(InvalidArgumentException.class,
                () -> consentService.getValidatedAndConfirmedConsents(Set.of(nonExistingInDbConsent)));
    }
}