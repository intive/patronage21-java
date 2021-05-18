package com.intive.patronative.service;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSearchValidator userSearchValidator;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public void saveUser(final UserDTO userDTO) {
    }

    public void updateUser(final UserEditDTO userEditDTO) {
        userValidator.validateUserData(userEditDTO);

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
        if (!UserValidator.isUsernameValid(login)) {
            throw new InvalidArgumentException("login", login);
        }

        return new UserResponseDTO(userMapper.mapToUserProfileDTO(userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("login", login))));
    }

}