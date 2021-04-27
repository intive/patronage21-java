package com.intive.patronative.service;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.User;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.validation.UserSearchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserSearchValidator userSearchValidator;

    public void saveUser(UserDTO dto) {
    }

    public void update(final User user) {
        log.info("Evoking service...");
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

}