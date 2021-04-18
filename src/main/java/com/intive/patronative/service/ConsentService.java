package com.intive.patronative.service;

import com.intive.patronative.dto.registration.ConsentDTO;
import com.intive.patronative.mapper.ConsentMapper;
import com.intive.patronative.repository.ConsentRepository;
import com.intive.patronative.repository.model.Consent;
import com.intive.patronative.validation.ConsentValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final ConsentMapper consentMapper;

    public Set<Consent> getValidatedAndConfirmedConsents(final Set<ConsentDTO> requestConsents) {
        final Set<Consent> requiredConsents = getConsentsByRequiredFlag(true);
        final Set<Consent> optionalConsents = getConsentsByRequiredFlag(false);
        final Map<BigDecimal, ConsentDTO> requestConsentsMap = consentMapper.convertRequestToMap(requestConsents);
        final Map<BigDecimal, Consent> requiredConsentsMap = consentMapper.convertEntitiesToMap(requiredConsents);
        final Map<BigDecimal, Consent> optionalConsentsMap = consentMapper.convertEntitiesToMap(optionalConsents);
        ConsentValidator.validateConsents(requestConsentsMap, requiredConsentsMap, optionalConsentsMap, false);

        final var optionalButConfirmedConsents =
                filterOptionalButConfirmedConsents(requestConsentsMap, requiredConsentsMap, optionalConsentsMap);
        return concatResultConsents(requiredConsents, optionalButConfirmedConsents);
    }

    private Set<Consent> getConsentsByRequiredFlag(final boolean isRequired) {
        return consentRepository.findAllByRequired(isRequired);
    }

    private Set<Consent> filterOptionalButConfirmedConsents(final Map<BigDecimal, ConsentDTO> requestConsents,
                                                            final Map<BigDecimal, Consent> requiredConsents,
                                                            final Map<BigDecimal, Consent> optionalConsents) {
        requestConsents.keySet().removeAll(requiredConsents.keySet());
        return requestConsents.entrySet().stream()
                .filter(requestConsent -> requestConsent.getKey() != null && requestConsent.getValue() != null)
                .filter(requestConsent -> requestConsent.getValue().isFlagConfirmed())
                .filter(requestConsent -> optionalConsents.containsKey(requestConsent.getKey()))
                .map(requestConsent -> optionalConsents.get(requestConsent.getKey()))
                .collect(Collectors.toSet());
    }

    private Set<Consent> concatResultConsents(final Set<Consent> requiredConsents,
                                              final Set<Consent> optionalConsents) {
        final Set<Consent> result = new HashSet<>();
        result.addAll(requiredConsents);
        result.addAll(optionalConsents);
        return result;
    }
}