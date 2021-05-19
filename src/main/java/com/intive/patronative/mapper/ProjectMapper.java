package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.ProjectResponseDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.ProjectRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public Optional<Set<Project>> mapToProjectSet(final Set<ProjectDTO> projectsDTO, final Set<Project> entityProjects) {
        return ((projectsDTO == null) || (entityProjects == null) || (entityProjects.isEmpty()))
                ? Optional.empty()
                : Optional.of(projectsDTO.stream()
                .map(projectDTO -> getProject(entityProjects, projectDTO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet()));
    }

    public List<ProjectDTO> mapToProjectDTOList(final Set<Project> entityProjects) {
        return Optional.ofNullable(entityProjects)
                .map(projects -> projects.stream()
                        .map(this::mapToProjectDTO)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(null);
    }

    public ProjectDTO mapToProjectDTO(final Project entityProject) {
        return Optional.ofNullable(entityProject)
                .map(project -> ProjectDTO.builder()
                        .name(project.getName())
                        .role(getProjectRole(project).map(ProjectRole::getName).orElse(null))
                        .build())
                .orElse(null);
    }

    private Optional<ProjectRole> getProjectRole(Project project) {
        return Optional.ofNullable(project)
                .map(Project::getProjectRoles)
                .flatMap(projectRoles -> projectRoles.stream().findAny());
    }

    private static Optional<Project> getProject(final Set<Project> entityProjects, final ProjectDTO projectDTO) {
        return entityProjects.stream()
                .map(project -> getEquallyNamedProject(projectDTO.getName(), project))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    private static Optional<Project> getEquallyNamedProject(final String dtoProjectName, final Project entityProject) {
        return Optional.of(entityProject).filter(project -> project.getName().equals(dtoProjectName));
    }

    public Set<ProjectResponseDTO> mapToProjectResponsesDTO(final Set<Project> projects) {
        return Optional.ofNullable(projects)
                .map(p -> p.stream()
                        .map(project -> new ProjectResponseDTO(project.getId(), project.getName()))
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

}