package com.intive.patronative.controller.frontend;

import com.intive.patronative.dto.ProjectsResponseDTO;
import com.intive.patronative.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/frontend-api/projects")
public class ProjectFrontController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ProjectsResponseDTO> getProjects(@RequestParam(required = false) final Integer year) {
        return ResponseEntity.ok(projectService.getProjectsByYear(year));
    }

}