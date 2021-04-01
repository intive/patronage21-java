package com.intive.patronative.controller;

import com.intive.patronative.model.User;
import com.intive.patronative.model.UserDto;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<UserDto>> displayUsersByRole(@Valid @RequestParam User.Role userRole) {
        if (userService.getUserByRole(userRole).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userService.getUserByRole(userRole), HttpStatus.OK);
        }
    }
}
