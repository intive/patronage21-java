package com.intive.patronative.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.intive.patronative.config.LocaleConfig;
import com.intive.patronative.exception.InvalidArgumentException;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class EnumDeserializer<T extends Enum<T>> extends StdDeserializer<T> implements ContextualDeserializer {

    private Class<T> enumType;

    public  EnumDeserializer(Class<Enum<T>> vc) {
        super(vc);
    }

    public EnumDeserializer() {
        this(null);
    }

    @Override
    public T deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) throws IOException {
        try {
            return Enum.valueOf(this.enumType, jsonParser.getText().toUpperCase());
        } catch (IllegalArgumentException exception) {
            final var fieldName = jsonParser.getCurrentName();
            final var rejectedValue = jsonParser.getText();
            final var objectName = this.enumType.getSimpleName();
            final var message = LocaleConfig.getLocaleMessage("userEnumDeserializerMessage") + " " +
                    Arrays.toString(this.enumType.getEnumConstants()).replaceAll("^.|.$", "");
            throw new InvalidArgumentException(Collections.singletonList(
                    new FieldError(objectName, fieldName, rejectedValue, false, null, null, message)));
        }
    }

    @Override
    public T getNullValue(final DeserializationContext deserializationContext) {
        String fieldName;
        try {
            fieldName = deserializationContext.getParser().currentName();
        } catch (IOException exception) {
            fieldName = this.enumType.getSimpleName();
        }
        final var objectName = this.enumType.getSimpleName();
        final var message = LocaleConfig.getLocaleMessage("userEnumDeserializerMessage") + " " +
                Arrays.toString(this.enumType.getEnumConstants()).replaceAll("^.|.$", "");
        throw new InvalidArgumentException(Collections.singletonList(
                new FieldError(objectName, fieldName, null, false, null, null, message)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public StdDeserializer<T> createContextual(final DeserializationContext context, final BeanProperty property) {
        this.enumType = (Class<T>) context.getContextualType().getRawClass();
        return this;
    }
}
