package com.intive.patronative.controller.frontend;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RestController
@RequestMapping("/frontend-api/users")
@RequiredArgsConstructor
public class UserFrontController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UsersDTO> search(@RequestParam(required = false) final String firstName,
                                           @RequestParam(required = false) final String lastName,
                                           @RequestParam(required = false) final String username,
                                           @RequestParam(required = false) final UserRole role) throws InvalidArgumentException {
        return ResponseEntity.ok(userService.searchUser(new UserSearchDTO(firstName, lastName, username, role)));
    }

    @PutMapping
    public void updateUser(@Valid @RequestBody final User user) {
        userService.update(user);
    }

}