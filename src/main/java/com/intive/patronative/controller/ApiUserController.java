package com.intive.patronative.controller;

import com.intive.patronative.model.UserDTO;
import com.intive.patronative.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private final UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public void updateUser(@Validated @RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
    }

}
