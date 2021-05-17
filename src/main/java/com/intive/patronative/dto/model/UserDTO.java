package com.intive.patronative.dto.model;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Value
public class UserDTO {
    @NotBlank(message = "Can not be empty") String login;
    @NotBlank(message = "Can not be empty") String firstName;
    @NotBlank(message = "Can not be empty") String lastName;
    @Email(message = "Must be correct email address")
    @NotBlank(message = "Can not be empty") String email;
    @NotBlank(message = "Can not be empty") String phoneNumber;
    @NotBlank(message = "Can not be empty") String gitHubUrl;
}
