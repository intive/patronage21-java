package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.ProjectResponseDTO;
import com.intive.patronative.repository.model.Project;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public Optional<Set<Project>> mapToProjectSet(final Set<ProjectDTO> projectsDTO, final Set<Project> availableProjects) {
        return ((projectsDTO == null) || CollectionUtils.isEmpty(availableProjects))
                ? Optional.empty()
                : Optional.of(projectsDTO.stream()
                .map(projectDTO -> getProject(projectDTO, availableProjects))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    private Project getProject(final ProjectDTO projectDTO, final Set<Project> availableProjects) {
        return ((projectDTO == null) || StringUtils.isEmpty(projectDTO.getName()))
                ? null
                : availableProjects.stream()
                .filter(project -> (projectDTO.getName().equalsIgnoreCase(project.getName())))
                .findFirst()
                .orElse(null);
    }

    public Set<ProjectResponseDTO> mapToProjectResponsesDTO(final Set<Project> entityProjects) {
        return Optional.ofNullable(entityProjects)
                .map(projects -> projects.stream()
                        .filter(Objects::nonNull)
                        .map(project -> new ProjectResponseDTO(project.getId(), project.getName()))
                        .collect(Collectors.toSet()))
                .orElseGet(Collections::emptySet);
    }

}