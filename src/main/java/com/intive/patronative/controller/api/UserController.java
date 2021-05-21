package com.intive.patronative.controller.api;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("/{login}")
    @Operation(summary = "Fetch user by login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch successful"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable(name = "login") final String login) {
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Creation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data passed"),
    })
    public ResponseEntity<String> saveUser(@Valid @RequestBody final UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User saved");
    }

    @PutMapping
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid data passed")
    })
    public ResponseEntity<Void> update(@RequestBody final UserEditDTO userEditDTO) {
        userService.updateUser(userEditDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{login}/deactivate")
    @Operation(summary = "Deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deactivation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login")
    })
    public ResponseEntity<Void> deactivateUser(@PathVariable(name = "login") final String login) {
        userService.deactivateUserByLogin(login);
        return ResponseEntity.ok().build();
    }

}