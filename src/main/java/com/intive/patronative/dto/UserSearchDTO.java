package com.intive.patronative.dto;

import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import lombok.Value;
import org.springframework.lang.Nullable;

@Value
public class UserSearchDTO {
    @Nullable
    String firstName;
    @Nullable
    String lastName;
    @Nullable
    String login;
    @Nullable
    UserRole role;
    @Nullable
    UserStatus status;
    @Nullable
    String technologyGroup;
    @Nullable
    String other;
}