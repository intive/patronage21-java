package com.intive.patronative.mapper;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserProfileDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.dto.UserProfileDTO;
import com.intive.patronative.repository.model.Profile;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper extends Mapper {

    private final ProjectMapper projectMapper;
    private final GroupMapper groupMapper;

    public User mapToEntity(final UserEditDTO userEditDTO, final User user, final Set<Project> availableProjects) {
        if (userEditDTO != null && user != null) {
            user.setFirstName(swapValues(userEditDTO.getFirstName(), user.getFirstName()));
            user.setLastName(swapValues(userEditDTO.getLastName(), user.getLastName()));
            user.setEmail(swapValues(userEditDTO.getEmail(), user.getEmail()));
            user.setPhoneNumber(swapValues(userEditDTO.getPhoneNumber(), user.getPhoneNumber()));
            user.setGitHubUrl(swapValues(userEditDTO.getGitHubUrl(), user.getGitHubUrl()));
            user.setProjects(projectMapper.mapToProjectSet(userEditDTO.getProjects(), availableProjects).orElse(user.getProjects()));
            if (user.getProfile() != null) {
                user.getProfile().setBio(swapValues(userEditDTO.getBio(), user.getProfile().getBio()));
            }
        }
        return user;
    }

    public UserProfileDTO mapToUserProfileDTO(final User entityUser) {
        return Optional.ofNullable(entityUser)
                .map(user -> UserProfileDTO
                        .builder()
                        .login(user.getLogin())
                        .image(Optional.ofNullable(entityUser.getProfile()).map(Profile::getImage).orElse(null))
                        .bio(Optional.ofNullable(entityUser.getProfile()).map(Profile::getBio).orElse(null))
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .gitHubUrl(user.getGitHubUrl())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .projects(projectMapper.mapToProjectDTOList(user.getProjects()))
                        .status(Optional.ofNullable(user.getStatus()).map(Status::getName).orElse(null))
                        .build())
                .orElse(null);
    }

    public UserDTO mapEntityToUserResponse(final User user) {
        return Optional.ofNullable(user)
                .map(u -> UserDTO.builder()
                        .login(u.getLogin())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .image(Optional.ofNullable(u.getProfile()).map(Profile::getImage).orElse(null))
                        .status(Optional.ofNullable(u.getStatus()).map(Status::getName).orElse(null))
                        .build())
                .orElse(null);
    }

    public UsersDTO mapEntitiesToUsersResponse(final List<User> users) {
        return Optional.ofNullable(users)
                .map(u -> new UsersDTO(u.stream()
                        .map(this::mapEntityToUserResponse)
                        .collect(Collectors.toList())))
                .orElse(null);
    }

    public User toUserRegistrationEntity(final User userEntity, final UserRegistrationRequestDTO requestUser) {
        if (userEntity != null && requestUser != null) {
            userEntity.setEmail(requestUser.getEmail());
            userEntity.setLogin(requestUser.getLogin());
            userEntity.setFirstName(requestUser.getFirstName());
            userEntity.setLastName(requestUser.getLastName());
            userEntity.setPhoneNumber(requestUser.getPhoneNumber());
            userEntity.setGitHubUrl(requestUser.getGitHubUrl());
        }
        return userEntity;
    }

    public UserRegistrationResponseDTO toUserRegistrationResponse(final User userEntity) {
        return Optional.ofNullable(userEntity)
                .map(user -> UserRegistrationResponseDTO.builder()
                        .email(user.getEmail())
                        .login(user.getLogin())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .gitHubUrl(user.getGitHubUrl())
                        .status(Optional.ofNullable(user.getStatus()).map(Status::getName).orElse(null))
                        .gender(Optional.ofNullable(user.getGender()).map(Gender::getName).orElse(null))
                        .role(Optional.ofNullable(user.getRole()).map(Role::getName).orElse(null))
                        .groups(groupMapper.toResponseList(user.getTechnologyGroups()))
                        .build())
                .orElse(null);
    }
}