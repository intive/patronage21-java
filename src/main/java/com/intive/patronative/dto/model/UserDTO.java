package com.intive.patronative.dto.model;

import com.intive.patronative.dto.profile.UserStatus;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class UserDTO {
    @NotBlank(message = "Can not be empty") String login;
    @NotBlank(message = "Can not be empty") String firstName;
    @NotBlank(message = "Can not be empty") String lastName;
    @NotBlank(message = "Can not be empty") byte[] image;
    @NotBlank(message = "Can not be empty") UserStatus status;
}