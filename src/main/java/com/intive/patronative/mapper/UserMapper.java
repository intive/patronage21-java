package com.intive.patronative.mapper;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.repository.model.Consent;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.TechnologyGroup;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.dto.UserProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper extends Mapper{

    private final ProjectMapper projectMapper;
    private final ConsentMapper consentMapper;
    private final GroupMapper groupMapper;

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

    public User toUserRegistrationEntity(final UserRegistrationRequestDTO user, final Role role,
                                                final Status status, final Gender gender, final Set<Consent> consents,
                                                final Set<TechnologyGroup> technologyGroups) {
        if (user == null) {
            return null;
        }
        final var userEntity = new User();
        userEntity.setEmail(user.getEmail());
        userEntity.setLogin(user.getLogin());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setTechnologyGroups(technologyGroups);
        userEntity.setRole(role);
        userEntity.setStatus(status);
        userEntity.setGender(gender);
        userEntity.setConsents(consents);
        return userEntity;
    }

    public UserRegistrationResponseDTO toUserRegistrationResponse(final User user) {
        if (user == null) {
            return null;
        }
        return UserRegistrationResponseDTO.builder()
                .email(user.getEmail())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(UserRole.valueOf(user.getRole().getName()))
                .status(UserStatus.valueOf(user.getStatus().getName()))
                .gender(UserGender.valueOf(user.getGender().getName()))
                .groups(groupMapper.toResponse(user.getTechnologyGroups()))
                .consents(consentMapper.toResponse(user.getConsents()))
                .build();
    }
}