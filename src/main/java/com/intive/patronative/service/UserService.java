package com.intive.patronative.service;

import com.intive.patronative.model.User;
import com.intive.patronative.dto.UserDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@NoArgsConstructor
public class UserService {

    public void update(UserDTO userDTO) {
        log.info("Evoking service to update user data...");
    }

    public void findBy(Map<String, String> params){
        log.info("Evoking service to find users...");
    }
}