package com.intive.patronative.dto;

import lombok.Value;
import org.springframework.lang.Nullable;

@Value
public class UserSearchDTO {
    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable String username;
}