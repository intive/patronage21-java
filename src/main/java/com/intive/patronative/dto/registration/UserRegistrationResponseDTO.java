package com.intive.patronative.dto.registration;

import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import lombok.Builder;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
@Builder
public class UserRegistrationResponseDTO {

    String firstName;
    String login;
    String lastName;
    String email;
    String phoneNumber;
    String gitHubUrl;
    UserRole role;
    UserStatus status;
    UserGender gender;

    @Builder.Default
    Set<TechnologyGroupDTO> groups = new HashSet<>();
}
