package com.intive.patronative.service;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.User;
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

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSearchValidator userSearchValidator;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;

    public void saveUser(final UserDTO userDTO) {
    }

    public void updateUser(final UserEditDTO userEditDTO) {
        userValidator.validateUserData(userEditDTO);

        final var user = userRepository.findByLogin(userEditDTO.getLogin()).orElseThrow(() ->
                new UserNotFoundException("login", userEditDTO.getLogin()));
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

    private User getUserFromDatabaseByLogin(final String login) {
        if (!userValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login", login));
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

}