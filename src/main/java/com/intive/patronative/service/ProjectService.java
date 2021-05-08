package com.intive.patronative.service;

import com.intive.patronative.dto.ProjectRolesDTO;
import com.intive.patronative.dto.ProjectsResponseDTO;
import com.intive.patronative.mapper.ProjectMapper;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.ProjectRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectMapper projectMapper;

    public ProjectsResponseDTO getProjectsByYear(final Integer year) {
        final var actualYear = Optional.ofNullable(year).orElseGet(() -> LocalDate.now().getYear());
        final var projects = projectRepository.getDistinctProjectsByYear(actualYear);
        final var projectResponsesDTO = projectMapper.mapToProjectResponsesDTO(projects);
        return new ProjectsResponseDTO(projectResponsesDTO);
    }

    public ProjectRolesDTO getRolesByProject(final BigDecimal projectId) {
        return new ProjectRolesDTO(projectRoleRepository.getRolesByProject(projectId));
    }

}