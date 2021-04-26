package com.intive.patronative.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.service.UserProviderTestData;
import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;
import com.intive.patronative.config.LocaleConfig;

import java.io.IOException;
import java.util.Optional;

import static com.intive.patronative.dto.registration.UserGender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumDeserializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializeCorrectInput_shouldReturnGender() throws IOException {
        // given
        final var json = objectMapper.writeValueAsString(UserProviderTestData.getUserDTO());

        // when
        final var result = objectMapper.readValue(json, UserRegistrationRequestDTO.class);

        // then
        assertEquals(FEMALE, result.getGender());
    }

    @Test
    void deserializeIncorrectInput_shouldThrowException() throws IOException {
        //given
        final var json = objectMapper.writeValueAsString(UserProviderTestData.getUserDTO());
        final var incorrectGenderValue = "foo";
        final var fieldName = "gender";
        final var expectedMessage =  LocaleConfig.getLocaleMessage("userEnumDeserializerMessage") + " MALE, FEMALE";

        final var node = objectMapper.readTree(json);
        final var objectNode = (ObjectNode) node; // mutable
        objectNode.put(fieldName, incorrectGenderValue);

        final var incorrectJson = objectNode.toString();

        // when / then
        final var jsonMappingException = assertThrows(JsonMappingException.class,
                () -> objectMapper.readValue(incorrectJson, UserRegistrationRequestDTO.class));

        final Optional<FieldError> fieldError =
                ((InvalidArgumentException) jsonMappingException.getCause()).getFieldErrors().stream().findFirst();

        // then
        assertThat(Optional.of(fieldError)).hasValueSatisfying(error -> {
            assertTrue(error.isPresent());
            assertTrue(error.get().getDefaultMessage() != null && error.get().getDefaultMessage().contains(expectedMessage));
            assertEquals(error.get().getRejectedValue(), incorrectGenderValue);
            assertEquals(error.get().getField(), fieldName);
        });
    }
}