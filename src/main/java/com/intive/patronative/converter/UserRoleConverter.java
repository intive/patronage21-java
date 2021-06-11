package com.intive.patronative.converter;

import com.intive.patronative.config.LocaleConfig;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.exception.InvalidArgumentException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Collections;

@Component
public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(String source) {
        try {
            return UserRole.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException exception) {
            final var roleMessage = LocaleConfig.getLocaleMessage("userRoleConverterMessage") + Arrays.toString(UserRole.values());

            throw new InvalidArgumentException(Collections.singletonList(
                    new FieldError("UserRole", "role", source, false, null, null, roleMessage)));
        }
    }

}