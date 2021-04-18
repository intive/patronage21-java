package com.intive.patronative.dto.registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConsentDTO {

    String text;

    @EqualsAndHashCode.Include
    BigDecimal consentId;

    boolean flagConfirmed;
}
