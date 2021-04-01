package com.intive.patronative.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gitHubUrl;
    private UserRole role;
    private UserStatus status;
}
