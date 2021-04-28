package com.intive.patronative.dto.model;

import lombok.Value;
import org.springframework.lang.Nullable;

@Value
public class UserDTO {
    @Nullable String login;
    @Nullable String firstName;
    @Nullable String lastName;
    @Nullable String email;
    @Nullable String phoneNumber;
    @Nullable String gitHubUrl;
}