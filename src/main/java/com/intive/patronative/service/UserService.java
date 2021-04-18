package com.intive.patronative.service;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.exception.AlreadyExistsException;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.GenderRepository;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.RoleRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import com.intive.patronative.repository.model.Consent;
import com.intive.patronative.repository.model.TechnologyGroup;
import com.intive.patronative.repository.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserSearchValidator userSearchValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final GroupService technologyGroupService;
    private final ConsentService consentService;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final GenderRepository genderRepository;

    public void updateUser(final UserEditDTO userEditDTO) {
        new UserValidator().validateUserData(userEditDTO);

        userRepository.save(userMapper.mapToEntity(userEditDTO,
                userRepository.findByLogin(userEditDTO.getLogin()).orElseThrow(() -> new UserNotFoundException("login", userEditDTO.getLogin())),
                projectRepository.findAllByYear(Calendar.getInstance().get(Calendar.YEAR))));
    }

    public UsersDTO searchUser(final UserSearchDTO userSearchDTO) throws InvalidArgumentException {
        log.info("Called userService");

        userSearchValidator.validateSearchParameters(userSearchDTO);

        return UsersDTO.builder()
                .users(Arrays.asList(new UserDTO("someLogin", "Lucas", "Smith", "lsmith@gmail.com", "123456789", "https://github.com/lsmith", "lsmith"),
                        new UserDTO("someLogin1", "Mark", "Marcuson", "marcuson@gmail.com", "98765421", "https://github.com/marcuson", "mmarcuson"),
                        new UserDTO("someLogin2", "Dick", "Hunt", "hdick@gmail.com", "654987321", "https://github.com/hdick", "hdick")))
                .build();
    }

    public UserResponseDTO getUserByLogin(final String login) {
        if (!UserValidator.isLoginValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return new UserResponseDTO(userMapper.mapToUserProfileDTO(userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("login", login))));
    }

    public UserRegistrationResponseDTO saveUser(final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        if (userRepository.existsByLogin(userRegistrationRequestDTO.getLogin())) {
            throw new AlreadyExistsException("login", userRegistrationRequestDTO.getLogin());
        }

        new UserValidator().validateRegistrationData(userRegistrationRequestDTO);

        final var validatedAndConfirmedConsents =
                consentService.getValidatedAndConfirmedConsents(userRegistrationRequestDTO.getConsents());

        final var userToSave =
                createUser(userRegistrationRequestDTO, validatedAndConfirmedConsents);
        return userMapper.toUserRegistrationResponse(userRepository.save(userToSave));
    }

    private User createUser(final UserRegistrationRequestDTO userRegistrationRequestDTO,
                            final Set<Consent> consentsToAssignToUser) {
        final var candidateRole = getRoleByName(UserRole.CANDIDATE);
        final var initialStatus = getStatusByName(UserStatus.ACTIVE);
        final var gender = getGenderByName(userRegistrationRequestDTO.getGender());
        final Set<TechnologyGroup> technologyGroupsToAssignToUser =
                technologyGroupService.getTechnologyGroupsEntitiesByList(userRegistrationRequestDTO.getGroups());

        return userMapper.toUserRegistrationEntity(userRegistrationRequestDTO, candidateRole, initialStatus, gender,
                consentsToAssignToUser, technologyGroupsToAssignToUser);
    }

    private Gender getGenderByName(final UserGender gender) {
        return genderRepository.findByName(gender.toString())
                .orElseThrow(() -> InvalidArgumentException.valueDoesNotExists("gender", gender.toString()));
    }

    private Status getStatusByName(final UserStatus status) {
        return statusRepository.findByName(status.toString())
                .orElseThrow(() -> InvalidArgumentException.valueDoesNotExists("status", status.toString()));
    }

    private Role getRoleByName(final UserRole role) {
        return roleRepository.findByName(role.toString())
                .orElseThrow(() -> InvalidArgumentException.valueDoesNotExists("role", role.toString()));
    }
}