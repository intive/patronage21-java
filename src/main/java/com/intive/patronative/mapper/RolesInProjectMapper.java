package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.ProjectRole;
import com.intive.patronative.repository.model.RolesInProject;
import com.intive.patronative.repository.model.RolesInProjectKey;
import com.intive.patronative.repository.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
public class RolesInProjectMapper {

    public Set<RolesInProject> mapToRolesInProjectSet(final User user, final Set<ProjectDTO> projectsDTO) {
        if ((user == null) || CollectionUtils.isEmpty(projectsDTO)) {
            return Collections.emptySet();
        }

        final var availableProjects = getAvailableProjects(user, projectsDTO);
        final var availableRoles = getAvailableRoles(availableProjects);

        return getRolesInProjects(user, availableProjects, availableRoles);
    }

    private Map<ProjectDTO, Project> getAvailableProjects(final User user, final Set<ProjectDTO> projectsDTO) {
        final Map<ProjectDTO, Project> projects = new HashMap<>();

        projectsDTO.stream()
                .filter(Objects::nonNull)
                .forEach(projectDTO -> projects.put(projectDTO, availableProject(projectDTO.getName(), user.getProjects())));

        return projects;
    }

    private Project availableProject(final String projectName, final Set<Project> availableProjects) {
        return StringUtils.isEmpty(projectName)
                ? null
                : Optional.ofNullable(availableProjects)
                .flatMap(projects -> projects.stream()
                        .filter(project -> nonNull(project) && projectName.equalsIgnoreCase(project.getName()))
                        .findFirst())
                .orElse(null);
    }

    private Map<ProjectDTO, ProjectRole> getAvailableRoles(final Map<ProjectDTO, Project> projectMap) {
        final Map<ProjectDTO, ProjectRole> projectRoles = new HashMap<>();

        projectMap.forEach((projectDTO, entityProject) -> Optional.ofNullable(entityProject).ifPresentOrElse(
                project -> projectRoles.put(projectDTO, availableProjectRole(projectDTO.getRole(), project.getProjectRoles())),
                () -> projectRoles.put(projectDTO, null)));

        return projectRoles;
    }

    private ProjectRole availableProjectRole(final String roleName, final Set<ProjectRole> availableProjectRoles) {
        return (StringUtils.isEmpty(roleName) || CollectionUtils.isEmpty(availableProjectRoles))
                ? null
                : availableProjectRoles.stream()
                .filter(projectRole -> roleName.equalsIgnoreCase(projectRole.getName()))
                .findFirst()
                .orElse(null);
    }

    private Set<RolesInProject> getRolesInProjects(final User user, final Map<ProjectDTO, Project> projectMap,
                                                   final Map<ProjectDTO, ProjectRole> projectRoles) {
        final Set<RolesInProject> rolesInProjects = new HashSet<>();

        projectMap.forEach((projectDTO, project) -> Optional.ofNullable(createRoleInProject(user, project, projectRoles.get(projectDTO)))
                .ifPresent(rolesInProjects::add));

        return rolesInProjects;
    }

    private RolesInProject createRoleInProject(final User user, final Project project, final ProjectRole projectRole) {
        return ((user == null) || (project == null) || (projectRole == null))
                ? null
                : RolesInProject.builder()
                .id(RolesInProjectKey.builder()
                        .projectId(project.getId())
                        .projectRoleId(projectRole.getId())
                        .userId(user.getId())
                        .build())
                .project(project)
                .projectRole(projectRole)
                .user(user)
                .build();
    }

}