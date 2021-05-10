package com.intive.patronative.service;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

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
                                final String technologyGroup, String other) {
        final var userSearchDTO = new UserSearchDTO(firstName, lastName, login, role, technologyGroup, other);
        userSearchValidator.validateSearchParameters(userSearchDTO);
        final List<User> fetchedUsers = userRepository.findAllUsers(userSearchDTO);

        return userMapper.mapEntitiesToUsersResponse(fetchedUsers);
    }

    public UserResponseDTO getUserByLogin(final String login) {
        return new UserResponseDTO(userMapper.mapToUserProfileDTO(getUserFromDatabaseByLogin(login)));
    }

    @Transactional
    public void storeUserInDatabase(final User user) {
        userRepository.save(user);
    }

    private User getUserFromDatabaseByLogin(String login) {
        if (!UserValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("login", login));
    }

    private boolean isUserActive(final User user) {
        return (user.getStatus() != null) && (user.getStatus().getName() != null)
                && UserStatus.ACTIVE.equals(user.getStatus().getName());
    }

}