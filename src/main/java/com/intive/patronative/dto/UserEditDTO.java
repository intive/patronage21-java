package com.intive.patronative.dto;

import lombok.Value;
import org.springframework.lang.Nullable;

import java.util.Set;

@Value
public class UserEditDTO {
    @Nullable
    String login;
    @Nullable
    String firstName;
    @Nullable
    String lastName;
    @Nullable
    String email;
    @Nullable
    String phoneNumber;
    @Nullable
    String gitHubUrl;
    @Nullable
    String bio;
    @Nullable
    Set<ProjectDTO> projects;
}