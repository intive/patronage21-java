package com.intive.patronative.service;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.exception.AlreadyExistsException;
import com.intive.patronative.exception.GenderNotFoundException;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.RoleNotFoundException;
import com.intive.patronative.exception.StatusNotFoundException;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.mapper.ProjectMapper;
import com.intive.patronative.mapper.RolesInProjectMapper;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.GenderRepository;
import com.intive.patronative.repository.ProfileRepository;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.RoleRepository;
import com.intive.patronative.repository.RolesInProjectRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Profile;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.RolesInProject;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.validation.TechnologyGroupsValidator;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSearchValidator userSearchValidator;
    private final TechnologyGroupsValidator technologyGroupsValidator;

    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    private final RolesInProjectMapper rolesInProjectMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final ProfileRepository profileRepository;
    private final RolesInProjectRepository rolesInProjectRepository;

    private final GroupService technologyGroupService;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;

    public void updateUser(final UserEditDTO userEditDTO) {
        userValidator.validateUserData(userEditDTO);

        final var user = userRepository.findByLogin(userEditDTO.getLogin()).orElseThrow(() ->
                new UserNotFoundException(userEditDTO.getLogin()));
        final var availableProjects = projectRepository.findAllByYear(Calendar.getInstance().get(Calendar.YEAR));
        final var userToStore = userMapper.mapToEntity(userEditDTO, user, availableProjects);

        updateUserInDatabase(userToStore, userEditDTO.getProjects());
    }

    @Transactional
    public void updateUserInDatabase(final User user, final Set<ProjectDTO> projectDTOs) {
        Optional.ofNullable(user)
                .ifPresent(u -> {
                    final var storedUser = storeUserInDatabase(u);
                    final var rolesInProjects = rolesInProjectMapper.mapToRolesInProjectSet(storedUser, projectDTOs);

                    rolesInProjectRepository.deleteAll(rolesInProjectRepository.findAllByUserId(storedUser.getId()));

                    Optional.ofNullable(rolesInProjects).ifPresent(rolesInProjectRepository::saveAll);
                });
    }

    public void deactivateUserByLogin(final String login) {
        final var user = getUserFromDatabaseByLogin(login);

        if (isUserActive(user)) {
            user.setStatus(statusRepository.findByName(UserStatus.INACTIVE).orElseGet(user::getStatus));
            storeUserInDatabase(user);
        }
    }

    public UsersDTO searchUsers(final String firstName, final String lastName, final String login, final UserRole role,
                                final UserStatus status, final String technologyGroup, final String other) {
        final var userSearchDTO = new UserSearchDTO(firstName, lastName, login, role, status, technologyGroup, other);

        userSearchValidator.validateSearchParameters(userSearchDTO);

        final List<User> fetchedUsers = userRepository.findAllUsers(userSearchDTO);

        return userMapper.mapEntitiesToUsersResponse(fetchedUsers);
    }

    public UserResponseDTO getUserByLogin(final String login) {
        final var user = getUserFromDatabaseByLogin(login);
        final var rolesInProjects = rolesInProjectRepository.findAllByUserId(user.getId());
        final var userProjects = getUserProjects(user.getProjects(), rolesInProjects);

        return new UserResponseDTO(userMapper.mapToUserProfileDTO(user, userProjects));
    }

    private Set<Project> getUserProjects(final Set<Project> projects, final Set<RolesInProject> rolesInProjects) {
        final var userProjects = new HashMap<String, Project>();

        addProjects(userProjects, projects);
        addRoles(userProjects, rolesInProjects);

        return new HashSet<>(userProjects.values());
    }

    private void addProjects(final Map<String, Project> userProjects, final Set<Project> projects) {
        Optional.ofNullable(projects)
                .ifPresent(items -> items.stream()
                        .filter(Objects::nonNull)
                        .forEach(project -> userProjects
                                .put(project.getName(), Project.builder()
                                        .name(project.getName())
                                        .projectRoles(new HashSet<>())
                                        .build())));
    }

    private void addRoles(final Map<String, Project> userProjects, final Set<RolesInProject> rolesInProjects) {
        if (nonNull(userProjects) && !CollectionUtils.isEmpty(rolesInProjects)) {
            final var projects = new HashSet<>(userProjects.values());

            rolesInProjects.stream()
                    .filter(rolesInProject -> nonNull(rolesInProject) && hasProject(rolesInProject.getProject(), projects))
                    .forEach(rolesInProject -> {
                        final var project = userProjects.get(rolesInProject.getProject().getName());
                        project.getProjectRoles().add(rolesInProject.getProjectRole());
                    });
        }
    }

    private boolean hasProject(final Project project, final Set<Project> projects) {
        if (nonNull(project) && !CollectionUtils.isEmpty(projects)) {
            return projects.stream()
                    .filter(Objects::nonNull)
                    .anyMatch(item -> item.getName().equalsIgnoreCase(project.getName()));
        }
        return false;
    }

    public void deleteImage(final String login) {
        final var user = getUserFromDatabaseByLogin(login);

        if (nonNull(user.getProfile()) && nonNull(user.getProfile().getImage())) {
            user.getProfile().setImage(null);

            storeUserInDatabase(user);
        }
    }

    public void uploadImage(final String login, final MultipartFile image) {
        userValidator.validateUserImage(image);
        final var user = getUserFromDatabaseByLogin(login);

        if (nonNull(user.getProfile())) {
            final byte[] profileImage = convertImageToBytes(image);
            user.getProfile().setImage(profileImage);
            storeUserInDatabase(user);
        }
    }

    @Transactional
    public User storeUserInDatabase(final User entityUser) {
        return Optional.ofNullable(entityUser)
                .map(userRepository::save)
                .orElse(null);
    }

    @Transactional
    public User registerNewUserInDatabase(final User user) {
        final var userSaved = userRepository.save(user);
        final var profileSaved = profileRepository.save(Profile.builder().userId(userSaved.getId()).build());
        userSaved.setProfile(profileSaved);
        return userSaved;
    }

    private User getUserFromDatabaseByLogin(final String login) {
        if (!userValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
    }

    private boolean isUserActive(final User user) {
        return (user.getStatus() != null) && (user.getStatus().getName() != null)
                && UserStatus.ACTIVE.equals(user.getStatus().getName());
    }

    private byte[] convertImageToBytes(final MultipartFile image) {
        try {
            return image.getBytes();
        } catch (final IOException e) {
            throw new InvalidArgumentException(List.of(new FieldError("String", "image", image.getContentType(), false, null, null, e.getMessage())));
        }
    }

    public UserRegistrationResponseDTO saveUser(final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        userValidator.validateRegistrationData(userRegistrationRequestDTO);
        technologyGroupsValidator.checkTechnologyGroups(userRegistrationRequestDTO.getGroups());
        if (userRepository.existsByLogin(userRegistrationRequestDTO.getLogin())) {
            throw new AlreadyExistsException("login", userRegistrationRequestDTO.getLogin());
        }

        final var userToSave = createUser(userRegistrationRequestDTO);
        return userMapper.toUserRegistrationResponse(registerNewUserInDatabase(userToSave));
    }

    private User createUser(final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final var userToSave = User.builder()
                .role(getRoleByName(UserRole.CANDIDATE))
                .status(getStatusByName(UserStatus.ACTIVE))
                .gender(getGenderByName(userRegistrationRequestDTO.getGender()))
                .technologyGroups(technologyGroupService.getTechnologyGroupsEntitiesByList(
                        Optional.ofNullable(userRegistrationRequestDTO.getGroups()).orElse(null)))
                .build();
        return userMapper.toUserRegistrationEntity(userToSave, userRegistrationRequestDTO);
    }

    private Gender getGenderByName(final UserGender gender) {
        return genderRepository.findByName(gender)
                .orElseThrow(() -> new GenderNotFoundException(gender.toString()));
    }

    private Status getStatusByName(final UserStatus status) {
        return statusRepository.findByName(status)
                .orElseThrow(() -> new StatusNotFoundException(status.toString()));
    }

    private Role getRoleByName(final UserRole role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new RoleNotFoundException(role.toString()));
    }

}