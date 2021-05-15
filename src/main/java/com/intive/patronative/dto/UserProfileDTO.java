package com.intive.patronative.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserProfileDTO {
    String login;
    byte[] image;
    String firstName;
    String lastName;
    String bio;
    List<ProjectDTO> projects;
    String email;
    String phoneNumber;
    String gitHubUrl;
}