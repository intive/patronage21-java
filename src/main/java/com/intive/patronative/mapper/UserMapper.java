package com.intive.patronative.mapper;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper extends Mapper{

    private final ProjectMapper projectMapper;

    public User mapToEntity(final UserEditDTO userEditDTO, final User user, final Set<Project> availableProjects) {
        if (userEditDTO != null) {
            user.setFirstName(swapValues(userEditDTO.getFirstName(), user.getFirstName()));
            user.setLastName(swapValues(userEditDTO.getLastName(), user.getLastName()));
            user.setEmail(swapValues(userEditDTO.getEmail(), user.getEmail()));
            user.setPhoneNumber(swapValues(userEditDTO.getPhoneNumber(), user.getPhoneNumber()));
            user.setGitHubUrl(swapValues(userEditDTO.getGitHubUrl(), user.getGitHubUrl()));
            user.getProfile().setBio(swapValues(userEditDTO.getBio(), user.getProfile().getBio()));
            user.setProjects(projectMapper.mapToProjectSet(userEditDTO.getProjects(), availableProjects).orElse(user.getProjects()));
        }
        return user;
    }

    public UserProfileDTO mapToUserProfileDTO(final User user) {
        return (user == null) ? UserProfileDTO.builder().build() : UserProfileDTO.builder()
                .login(user.getLogin())
                .image(user.getProfile().getImage())
                .bio(user.getProfile().getBio())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gitHubUrl(user.getGitHubUrl())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .projects(projectMapper.mapToProjectDTOList(user.getProjects()))
                .build();
    }

    public UserDTO mapEntityToUserResponse(final User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getLogin(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getGitHubUrl()
        );
    }

    public UsersDTO mapEntitiesToUsersResponse(final List<User> users) {
        if (users == null) {
            return null;
        }
        final var usersDTO = users.stream()
                .map(this::mapEntityToUserResponse)
                .collect(Collectors.toList());
        return new UsersDTO(usersDTO);
    }
}