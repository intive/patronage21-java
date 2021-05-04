package com.intive.patronative.controller.frontend;

import com.intive.patronative.dto.model.GroupsDTO;
import com.intive.patronative.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/frontend-api/groups")
public class GroupFrontController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<GroupsDTO> getGroups() {
        return ResponseEntity.ok().body(groupService.getGroups());
    }

}
