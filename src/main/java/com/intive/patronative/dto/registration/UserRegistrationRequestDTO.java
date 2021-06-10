package com.intive.patronative.dto.registration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserRegistrationRequestDTO {

    String firstName;
    String login;
    String lastName;
    String email;
    String phoneNumber;
    String gitHubUrl;
    UserGender gender;

    @Builder.Default
    Set<TechnologyGroupDTO> groups = new HashSet<>();
}
