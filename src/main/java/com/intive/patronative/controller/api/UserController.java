package com.intive.patronative.controller.api;

import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.profile.User;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<String> saveUser(@Valid @RequestBody final UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User saved");
    }

    @PutMapping
    public void update(@Valid @RequestBody final User userDTO) {
        userService.update(userDTO);
    }

}