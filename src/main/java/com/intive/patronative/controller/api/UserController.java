package com.intive.patronative.controller.api;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserResponseDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.dto.registration.UserRegistrationResponseDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Search users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search successful"),
            @ApiResponse(responseCode = "422", description = "Invalid data passed")
    })
    public ResponseEntity<UsersDTO> search(@RequestParam(required = false) final String firstName,
                                           @RequestParam(required = false) final String lastName,
                                           @RequestParam(required = false) final String login,
                                           @RequestParam(required = false) final UserRole role,
                                           @RequestParam(required = false) final UserStatus status,
                                           @RequestParam(required = false) final String technologyGroup,
                                           @RequestParam(required = false) final String other) {
        return ResponseEntity.ok(userService.searchUsers(firstName, lastName, login, role, status, technologyGroup, other));
    }

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
            @ApiResponse(responseCode = "201", description = "Creation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data passed"),
    })
    public ResponseEntity<UserRegistrationResponseDTO> saveUser(
            @RequestBody final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final var response = userService.saveUser(userRegistrationRequestDTO);
        return ResponseEntity.status(CREATED).body(response);
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

    @PostMapping("/{login}/image")
    @Operation(summary = "Upload user profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login or image")
    })
    public ResponseEntity<Void> uploadImage(@PathVariable(name = "login") final String login, @RequestParam(required = false) final MultipartFile image) {
        userService.uploadImage(login, image);
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

    @PatchMapping("/{login}/image")
    @Operation(summary = "Update user profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login or image")
    })
    public ResponseEntity<Void> updateImage(@PathVariable(name = "login") final String login, @RequestParam(required = false) final MultipartFile image) {
        userService.uploadImage(login, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{login}/image")
    @Operation(summary = "Delete user's image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image removed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login")
    })
    public ResponseEntity<Void> deleteImage(@PathVariable(name = "login") final String login) {
        userService.deleteImage(login);
        return ResponseEntity.ok().build();
    }
}