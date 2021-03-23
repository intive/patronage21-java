package com.intive.patronative.service;

import com.intive.patronative.model.User;
import com.intive.patronative.model.UserDto;
import com.intive.patronative.model.UserDtoList;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDisplayService {
    private final List<User> userList = new ArrayList<>();

    // set bean to preload default set of data
    @Bean
    public void setUsers() {
        userList.add(new User("Tomasz", "Wisniewski", "abc@gmail.com", "123456789",
                "gitURL.git", "user1", User.Role.CANDIDATE, User.Status.ACTIVE));
        userList.add(new User("Jan", "Kowalski", "def@gmail.com", "111111111",
                "gitURL2.git", "user2", User.Role.LEADER, User.Status.ACTIVE));
        userList.add(new User("Aleksandra", "Nowak", "ghi@gmail.com", "999999999",
                "gitURL3.git", "user3", User.Role.CANDIDATE, User.Status.ACTIVE));
    }

    //search and return users with given role
    public UserDtoList getUserByRole(User.Role userRole) {
        return new UserDtoList(userList.stream()
                .filter(role -> role.getRole().equals(userRole))
                .map(this::mapUserToUserDto)
                .collect(Collectors.<UserDto>toList()));
    }

    //map user entity to user dto
    private UserDto mapUserToUserDto(User user) {
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