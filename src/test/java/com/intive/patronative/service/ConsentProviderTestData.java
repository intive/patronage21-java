package com.intive.patronative.service;


import com.intive.patronative.dto.registration.ConsentDTO;
import com.intive.patronative.repository.model.Consent;

import java.math.BigDecimal;

public class ConsentProviderTestData {

    public static Consent getFirstConsentEntity(final boolean isRequired) {
        final var consent = new Consent();
        consent.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        consent.setRequired(isRequired);
        consent.setConsentId(BigDecimal.valueOf(1));
        return consent;
    }

    public static Consent getSecondConsentEntity(final boolean isRequired) {
        final var consent = new Consent();
        consent.setText("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium " +
                "doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi " +
                "architecto beatae vitae dicta sunt explicabo. ");
        consent.setRequired(isRequired);
        consent.setConsentId(BigDecimal.valueOf(2));
        return consent;
    }

    public static ConsentDTO getFirstConsentDTO(final boolean isConfirmed) {
        return ConsentDTO.builder()
                .text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                        "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                .flagConfirmed(isConfirmed)
                .consentId(BigDecimal.valueOf(1))
                .build();
    }

    public static ConsentDTO getSecondConsentDTO(final boolean isConfirmed) {
        return ConsentDTO.builder()
                .text("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium " +
                        "doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi " +
                        "architecto beatae vitae dicta sunt explicabo. ")
                .flagConfirmed(isConfirmed)
                .consentId(BigDecimal.valueOf(2))
                .build();
    }
}
