package com.intive.patronative.controller.api;

import com.intive.patronative.dto.ProjectRolesDTO;
import com.intive.patronative.dto.ProjectsResponseDTO;
import com.intive.patronative.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @Operation(summary = "Fetch projects by year")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch successful"),
            @ApiResponse(responseCode = "422", description = "Invalid data passed")
    })
    public ResponseEntity<ProjectsResponseDTO> getProjects(@RequestParam(required = false) final Integer year) {
        return ResponseEntity.ok(projectService.getProjectsByYear(year));
    }

    @GetMapping("/{id}/roles")
    @Operation(summary = "Fetch roles by project id")
    @ApiResponse(responseCode = "200", description = "Fetch successful")
    public ResponseEntity<ProjectRolesDTO> getRolesByProject(@PathVariable(name = "id") final BigDecimal id) {
        return ResponseEntity.ok(projectService.getRolesByProject(id));
    }

}