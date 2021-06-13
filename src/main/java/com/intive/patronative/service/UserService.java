package com.intive.patronative.service;

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
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.GenderRepository;
import com.intive.patronative.repository.ProfileRepository;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.RoleRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Profile;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.validation.TechnologyGroupsValidator;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSearchValidator userSearchValidator;
    private final TechnologyGroupsValidator technologyGroupsValidator;

    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final ProfileRepository profileRepository;

    private final GroupService technologyGroupService;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;

    public void updateUser(final UserEditDTO userEditDTO) {
        userValidator.validateUserData(userEditDTO);

        final var user = userRepository.findByLogin(userEditDTO.getLogin()).orElseThrow(() ->
                new UserNotFoundException(userEditDTO.getLogin()));
        final var currentYear = projectRepository.findAllByYear(Calendar.getInstance().get(Calendar.YEAR));

        storeUserInDatabase(userMapper.mapToEntity(userEditDTO, user, currentYear));
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
        return new UserResponseDTO(userMapper.mapToUserProfileDTO(getUserFromDatabaseByLogin(login)));
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
    public void storeUserInDatabase(final User user) {
        userRepository.save(user);
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