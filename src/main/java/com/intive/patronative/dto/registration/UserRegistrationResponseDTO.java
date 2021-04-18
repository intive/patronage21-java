package com.intive.patronative.dto.registration;

import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class UserRegistrationResponseDTO {

    String firstName;
    String login;
    String lastName;
    String email;
    String phoneNumber;
    UserRole role;
    UserStatus status;
    UserGender gender;

    @Builder.Default
    List<TechnologyGroupDTO> groups = new ArrayList<>();

    @Builder.Default
    List<ConsentDTO> consents = new ArrayList<>();
}
