package com.intive.patronative.utils;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserDto;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gitHubUrl(user.getGitHubUrl())
                .userName(user.getUserName())
                .build();
    }
}
