package com.intive.patronative.controller.api;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public void update(@Valid @RequestBody User userDTO){
        userService.update(userDTO);
    }

}