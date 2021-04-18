package com.intive.patronative.validation;

import com.intive.patronative.dto.registration.ConsentDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.repository.model.Consent;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsentValidator {

    private ConsentValidator() {
    }

    private static final int CONSENT_TEXT_MAX_LENGTH = 512;
    private static final int CONSENTS_LIST_MIN_SIZE = 1;
    private static final int CONSENTS_LIST_MAX_SIZE = 2;
    private static final String CONSENT_TEXT_LENGTH_MESSAGE =
            "Consent text cannot be empty, up to " + ConsentValidator.CONSENT_TEXT_MAX_LENGTH + " characters.";
    private static final String CONSENTS_LIST_SIZE_MESSAGE =
            "Acceptable consents list size is between " + ConsentValidator.CONSENTS_LIST_MIN_SIZE +
                    " to " + ConsentValidator.CONSENTS_LIST_MAX_SIZE + ", entries without consentId are ignored.";
    private static final String REQUIRED_CONSENT_NOT_CONFIRMED_MESSAGE = "Required consent is not confirmed.";
    private static final String CONSENT_NOT_EXISTS_MESSAGE = "Consent does not exists.";

    public static void validateConsents(final Map<BigDecimal, ConsentDTO> requestConsents,
                                        final Map<BigDecimal, Consent> requiredConsents,
                                        final Map<BigDecimal, Consent> optionalConsents,
                                        final boolean checkRequiredOnly) {
        final var safeRequiredConsents = getSafeSetOfIdsFromEntities(requiredConsents);
        final var safeOptionalConsents = getSafeSetOfIdsFromEntities(optionalConsents);
        final var safeRequestConsents = getSafeMapRequestConsents(requestConsents);
        validate(safeRequestConsents, safeRequiredConsents, safeOptionalConsents, checkRequiredOnly);
    }

    private static void validate(final Map<BigDecimal, ConsentDTO> requestConsents,
                                 final Set<BigDecimal> requiredConsents,
                                 final Set<BigDecimal> optionalConsents,
                                 final boolean checkRequiredOnly) {
        final List<FieldError> errors = Stream.of(
                Stream.of(checkConsentsSize(requestConsents)),
                checkConsentTextErrors(requestConsents).stream(),
                checkRequiredConsentsHasBeenConfirmed(requiredConsents, requestConsents).stream(),
                checkOptionalConsentsExistsIfFlagSet(
                        optionalConsents,
                        getOnlyOptionalFromRequest(requestConsents, requiredConsents),
                        checkRequiredOnly).stream())
                .flatMap(errorStream -> errorStream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new InvalidArgumentException(errors);
        }
    }

    private static Set<BigDecimal> getSafeSetOfIdsFromEntities(final Map<BigDecimal, Consent> consents) {
        if (consents == null) {
            return Collections.emptySet();
        }
        return consents.keySet().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private static Map<BigDecimal, ConsentDTO> getSafeMapRequestConsents(final Map<BigDecimal, ConsentDTO> consents) {
        if (consents == null) {
            return Collections.emptyMap();
        }
        return consents.entrySet().stream()
                .filter(consent -> consent.getKey() != null && consent.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private static List<FieldError> checkConsentTextErrors(final Map<BigDecimal, ConsentDTO> requestConsents) {
        return requestConsents.values().stream()
                .filter(Objects::nonNull)
                .map(ConsentValidator::checkConsentText)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static FieldError checkConsentText(final ConsentDTO requestConsent) {
        return (isConsentTextValid(requestConsent.getText())) ?
                null :
                getFieldError(getConsentIdAsStringIfNotNull(requestConsent.getConsentId()), CONSENT_TEXT_LENGTH_MESSAGE);
    }

    public static boolean isConsentTextValid(final String consentText) {
        return (consentText == null) || (!consentText.isEmpty() && consentText.length() <= CONSENT_TEXT_MAX_LENGTH);
    }

    private static FieldError checkConsentsSize(final Map<BigDecimal, ConsentDTO> requestConsents) {
        final var size = requestConsents == null ? 0 : requestConsents.size();
        return (size < CONSENTS_LIST_MIN_SIZE || size > CONSENTS_LIST_MAX_SIZE) ?
                getFieldError(null, CONSENTS_LIST_SIZE_MESSAGE) :
                null;
    }

    private static List<FieldError> checkRequiredConsentsHasBeenConfirmed(
            final Set<BigDecimal> requiredConsents,
            final Map<BigDecimal, ConsentDTO> requestConsents) {
        return requiredConsents.stream()
                .map(requiredConsent -> checkRequiredConsentIsConfirmed(requiredConsent, requestConsents))
                .collect(Collectors.toList());
    }

    private static FieldError checkRequiredConsentIsConfirmed(final BigDecimal requiredConsentId,
                                                              final Map<BigDecimal, ConsentDTO> requestConsents) {
        final var requestConsent = requestConsents.getOrDefault(requiredConsentId, null);
        return requestConsent != null && requestConsent.isFlagConfirmed() ?
                null :
                getFieldError(getConsentIdAsStringIfNotNull(requiredConsentId), REQUIRED_CONSENT_NOT_CONFIRMED_MESSAGE);
    }

    private static List<FieldError> checkOptionalConsentsExistsIfFlagSet(final Set<BigDecimal> optionalConsents,
                                                                         final Map<BigDecimal, ConsentDTO> requestConsents,
                                                                         final boolean checkRequiredOnly) {
        if (checkRequiredOnly) {
            return Collections.emptyList();
        }
        return requestConsents.values().stream()
                .map(consentRequest -> checkRequestConsentExistsAsOptional(consentRequest, optionalConsents))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static FieldError checkRequestConsentExistsAsOptional(final ConsentDTO requestConsent,
                                                                  final Set<BigDecimal> optionalConsents) {
        return isRequestConsentExistsAsOptional(requestConsent, optionalConsents) ?
                null :
                getFieldError(getConsentIdAsStringIfNotNull(requestConsent.getConsentId()), CONSENT_NOT_EXISTS_MESSAGE);
    }

    private static boolean isRequestConsentExistsAsOptional(final ConsentDTO requestConsent,
                                                            final Set<BigDecimal> optionalConsents) {
        return !(optionalConsents == null || !optionalConsents.contains(requestConsent.getConsentId()));
    }

    private static Map<BigDecimal, ConsentDTO> getOnlyOptionalFromRequest(final Map<BigDecimal, ConsentDTO> requestConsents,
                                                                          final Set<BigDecimal> requiredConsents) {
        if (requiredConsents == null) {
            return requestConsents;
        }
        final Map<BigDecimal, ConsentDTO> requestConsentsOptionalOnly = new HashMap<>(requestConsents);
        requestConsentsOptionalOnly.keySet().removeAll(requiredConsents);
        return requestConsentsOptionalOnly;
    }

    private static String getConsentIdAsStringIfNotNull(final BigDecimal consentId) {
        return consentId != null ? consentId.toString() : null;
    }

    private static FieldError getFieldError(final String fieldValue, final String message) {
        return new FieldError("String", "consents", fieldValue, false, null, null, message);
    }
}