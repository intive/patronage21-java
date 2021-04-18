package com.intive.patronative.mapper;

import com.intive.patronative.dto.registration.ConsentDTO;
import com.intive.patronative.repository.model.Consent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Component
public class ConsentMapper {

    public ConsentDTO toResponse(final Consent consent) {
        if (consent == null) {
            return null;
        }
        return ConsentDTO.builder()
                .text(consent.getText())
                .consentId(consent.getConsentId())
                .flagConfirmed(consent.isRequired())
                .build();
    }

    public List<ConsentDTO> toResponse(final Set<Consent> consents) {
        if (consents == null) {
            return Collections.emptyList();
        }
        return consents
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Map<BigDecimal, ConsentDTO> convertRequestToMap(final Set<ConsentDTO> consents) {
        if (consents == null) {
            return Collections.emptyMap();
        }
        return consents.stream()
                .filter(consent -> consent.getConsentId() != null)
                .collect(toMap(ConsentDTO::getConsentId, Function.identity()));
    }

    public Map<BigDecimal, Consent> convertEntitiesToMap(final Set<Consent> consents) {
        if (consents == null) {
            return Collections.emptyMap();
        }
        return consents.stream()
                .filter(consent -> consent.getConsentId() != null)
                .collect(toMap(Consent::getConsentId, Function.identity()));
    }
}
