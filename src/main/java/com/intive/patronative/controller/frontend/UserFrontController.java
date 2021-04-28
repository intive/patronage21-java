package com.intive.patronative.controller.frontend;

import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontend-api/users")
@RequiredArgsConstructor
public class UserFrontController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UsersDTO> search(@RequestParam(required = false) final String firstName,
                                           @RequestParam(required = false) final String lastName,
                                           @RequestParam(required = false) final String login,
                                           @RequestParam(required = false) final UserRole role) {
        return ResponseEntity.ok(userService.searchUsers(firstName, lastName, login, role));
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable(name = "login") final String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody final UserEditDTO userEditDTO) {
        userService.updateUser(userEditDTO);
        return ResponseEntity.ok().build();
    }

}