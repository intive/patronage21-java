package com.intive.patronative.service;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserDto;
import com.intive.patronative.dto.profile.UserRole;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
public class UserService {

    public void update(User userDTO) {
        log.info("Evoking service...");
    }

    public List<UserDto> getUserByRole(UserRole userRole) {
        log.info("Filtering users...");
        return new ArrayList<>();
    }
}
