package com.intive.patronative.service;

import com.intive.patronative.model.User;
import com.intive.patronative.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    public UserService() {

    }

    public void updateUser(UserDTO userDTO) {
        log.info("Evoking service...");
    }


}
