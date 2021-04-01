package com.intive.patronative.controller.api;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserDto;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public void update(@Valid @RequestBody User userDTO) {
        userService.update(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUserByRole(@Valid @RequestParam UserRole userRole) {
        if (userService.getUserByRole(userRole) == null || userService.getUserByRole(userRole).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userService.getUserByRole(userRole), HttpStatus.OK);
        }
    }
}