package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProjectMapperTest {

    @Test
    void toProjectSet_shouldReturnProjectSet() {
        final var projectsDTOList = exampleProjectsDTOSets();
        final var expectedResult = expectedResults();
        for (int i = 0; i < expectedResult.size(); i++) {
            assertEquals(expectedResult.get(i), ProjectMapper.toProjectSet(projectsDTOList.get(i), exampleEntityProjectsSet()).orElseThrow());
        }
    }

    @Test
    void toProjectSet_shouldReturnEmptyOptional() {
        assertEquals(Optional.empty(), ProjectMapper.toProjectSet(null, null));
        assertEquals(Optional.empty(), ProjectMapper.toProjectSet(null, Collections.emptySet()));
        assertEquals(Optional.empty(), ProjectMapper.toProjectSet(null, exampleEntityProjectsSet()));
        assertEquals(Optional.empty(), ProjectMapper.toProjectSet(exampleProjectsDTOSets().get(0), null));
        assertEquals(Optional.empty(), ProjectMapper.toProjectSet(exampleProjectsDTOSets().get(0), Collections.emptySet()));
    }

    private List<Set<ProjectDTO>> exampleProjectsDTOSets() {
        return List.of(
                Collections.emptySet(),
                new HashSet<>(Arrays.asList(getProjectDTO(null), getProjectDTO("Projekt II"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt I"), getProjectDTO("Projekt II"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt I"), getProjectDTO("Projekt II"), getProjectDTO("Projekt III"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt I"), getProjectDTO("Projekt II"), getProjectDTO("Projekt III"), getProjectDTO("Projekt IV"))), //non existing project
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt I"), getProjectDTO("Projekt III"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt II"), getProjectDTO("Projekt III"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt II"), getProjectDTO("Projekt I"))),
                new HashSet<>(Arrays.asList(getProjectDTO("Projekt II"), getProjectDTO("Projekt I"), getProjectDTO("Projekt I")))
        );
    }

    private List<Set<Project>> expectedResults() {
        return List.of(
                Collections.emptySet(),
                new HashSet<>(Collections.singletonList(getProject("Projekt II"))),
                new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt II"))),
                new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III"))),
                new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III"))),
                new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt III"))),
                new HashSet<>(Arrays.asList(getProject("Projekt II"), getProject("Projekt III"))),
                new HashSet<>(Arrays.asList(getProject("Projekt II"), getProject("Projekt I"))),
                new HashSet<>(Arrays.asList(getProject("Projekt II"), getProject("Projekt I")))
        );
    }

    private Set<Project> exampleEntityProjectsSet() {
        return new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III")));
    }

    private ProjectDTO getProjectDTO(final String name) {
        return new ProjectDTO(name, null);
    }

    private Project getProject(final String name) {
        final var project = new Project();
        project.setName(name);
        project.setYear(Calendar.getInstance().get(Calendar.YEAR));
        return project;
    }

}