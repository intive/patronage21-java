package com.intive.patronative.utils;

import com.intive.patronative.model.User;
import com.intive.patronative.model.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .githubUrl(user.getGithubUrl())
                .userName(user.getUserName())
                .build();
    }
}
