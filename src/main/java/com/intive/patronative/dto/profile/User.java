package com.intive.patronative.dto.profile;

import lombok.Data;

@Data
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
