package com.intive.patronative.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<Groups> groups() {
        Optional<Groups> groups = Optional.of(groupService.getGroups());

        if (groups.isEmpty() || groups.get().getGroups().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(groups.get());
    }

}