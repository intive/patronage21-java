package com.intive.patronative.service;

import com.intive.patronative.dto.profile.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class UserService {

    public void update(final User user) {
        log.info("Evoking service...");
    }

}