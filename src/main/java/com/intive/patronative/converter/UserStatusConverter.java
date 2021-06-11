package com.intive.patronative.converter;

import com.intive.patronative.config.LocaleConfig;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.exception.InvalidArgumentException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Collections;

@Component
public class UserStatusConverter implements Converter<String, UserStatus> {

    @Override
    public UserStatus convert(final String source) {
        try {
            return UserStatus.valueOf(source.toUpperCase());
        } catch (final IllegalArgumentException exception) {
            final var statusMessage = LocaleConfig.getLocaleMessage("userStatusConverterMessage") + Arrays.toString(UserStatus.values());

            throw new InvalidArgumentException(Collections.singletonList(
                    new FieldError("UserStatus", "status", source, false, null, null, statusMessage)));
        }
    }

}