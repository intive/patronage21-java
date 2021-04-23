package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static Optional<Set<Project>> toProjectSet(final Set<ProjectDTO> projectsDTO, final Set<Project> entityProjects) {
        return ((projectsDTO == null) || (entityProjects == null) || (entityProjects.isEmpty()))
                ? Optional.empty()
                : Optional.of(projectsDTO.stream()
                .map(projectDTO -> getProject(entityProjects, projectDTO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet()));
    }

    private static Optional<Project> getProject(final Set<Project> entityProjects, final ProjectDTO projectDTO) {
        final var projects = entityProjects.stream()
                .map(project -> getEquallyNamedProject(projectDTO.getName(), project))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return projects.isEmpty() ? Optional.empty() : Optional.of(projects.get(0));
    }

    private static Optional<Project> getEquallyNamedProject(final String dtoProjectName, final Project entityProject) {
        return Optional.of(entityProject).filter(project -> project.getName().equals(dtoProjectName));
    }

}