package com.intive.patronative.model;

import lombok.Data;

@Data
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String githubUrl;
    private String userName;
    private Role role;
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public enum Role {
        LEADER,
        CANDIDATE
    }
}
