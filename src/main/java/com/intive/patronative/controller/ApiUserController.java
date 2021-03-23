package com.intive.patronative.controller;

import com.intive.patronative.model.UserDTO;
import com.intive.patronative.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;

    @PutMapping
    public void updateUser(@Valid @RequestBody UserDTO userDTO){
        userService.update(userDTO);
    }

}