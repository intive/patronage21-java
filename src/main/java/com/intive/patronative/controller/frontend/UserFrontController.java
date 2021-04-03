package com.intive.patronative.controller.frontend;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/frontend-api/users")
@RequiredArgsConstructor
public class UserFrontController {

    private final UserService userService;

    @PutMapping
    public void updateUser(@Valid @RequestBody final User user) {
        userService.update(user);
    }

}
