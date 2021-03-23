package com.intive.patronative.controller;

import com.intive.patronative.model.User;
import com.intive.patronative.service.UserDisplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDisplayController {
    @Autowired
    private UserDisplayService userDisplayService;

    @GetMapping("/api/users")
    public ResponseEntity<?> displayUsersByRole(@RequestParam User.Role userRole) {
        return new ResponseEntity<>(userDisplayService.getUserByRole(userRole), HttpStatus.OK);
    }
}
