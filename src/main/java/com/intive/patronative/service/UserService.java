package com.intive.patronative.service;

import com.intive.patronative.model.User;
import com.intive.patronative.model.UserDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class UserService {

    public void update(UserDTO userDTO) {
        log.info("Evoking service...");
    }

}