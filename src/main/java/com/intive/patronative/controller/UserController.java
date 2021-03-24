package com.intive.patronative.controller;

import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User saved");
    }
}
