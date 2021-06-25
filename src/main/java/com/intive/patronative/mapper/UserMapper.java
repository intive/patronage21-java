package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserProfileDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Profile;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trim;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ProjectMapper projectMapper;
    private final GroupMapper groupMapper;

    public User mapToEntity(final UserEditDTO userEditDTO, final User user, final Set<Project> availableProjects) {
        if (userEditDTO != null && user != null) {
            user.setFirstName(Optional.ofNullable(userEditDTO.getFirstName()).orElse(user.getFirstName()));
            user.setLastName(Optional.ofNullable(userEditDTO.getLastName()).orElse(user.getLastName()));
            user.setEmail(Optional.ofNullable(userEditDTO.getEmail()).orElse(user.getEmail()));
            user.setPhoneNumber(Optional.ofNullable(userEditDTO.getPhoneNumber()).orElse(user.getPhoneNumber()));
            user.setGitHubUrl(Optional.ofNullable(userEditDTO.getGitHubUrl()).orElse(user.getGitHubUrl()));
            user.setProjects(projectMapper.mapToProjectSet(userEditDTO.getProjects(), availableProjects).orElse(user.getProjects()));
            if (user.getProfile() != null) {
                user.getProfile().setBio(Optional.ofNullable(userEditDTO.getBio()).orElse(user.getProfile().getBio()));
            }
        }
        return user;
    }

    public UserProfileDTO mapToUserProfileDTO(final User entityUser, final Set<Project> projects) {
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
                        .status(Optional.ofNullable(user.getStatus()).map(Status::getName).orElse(null))
                        .projects(mapToProjectDTOSet(projects))
                        .build())
                .orElse(null);
    }

    private Set<ProjectDTO> mapToProjectDTOSet(final Set<Project> projects) {
        final var projectDTOs = new HashSet<ProjectDTO>();

        if (!CollectionUtils.isEmpty(projects)) {
            projects.stream()
                    .filter(Objects::nonNull)
                    .forEach(project -> addProjectDTO(projectDTOs, project));
        }

        return projectDTOs;
    }

    private void addProjectDTO(final Set<ProjectDTO> destination, final Project project) {
        if (nonNull(project) && nonNull(destination)) {
            if (CollectionUtils.isEmpty(project.getProjectRoles()) && isNotBlank(project.getName())) {
                destination.add(ProjectDTO.builder().name(trim(project.getName())).build());
            }

            Optional.ofNullable(project.getProjectRoles())
                    .ifPresent(roles -> roles.stream().filter(Objects::nonNull)
                            .forEach(role -> destination.add(ProjectDTO.builder()
                                    .name(project.getName())
                                    .role(role.getName())
                                    .build())));

        }
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