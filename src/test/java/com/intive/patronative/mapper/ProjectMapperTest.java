package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.ProjectRole;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProjectMapperTest {

    private final ProjectMapper projectMapper = new ProjectMapper();

    @ParameterizedTest
    @MethodSource("mapToProjectSet_shouldNotThrow_data")
    void mapToProjectSet_shouldNotThrow(final Set<ProjectDTO> projectsDTO, final Set<Project> availableProjects) {
        assertDoesNotThrow(() -> projectMapper.mapToProjectSet(projectsDTO, availableProjects));
    }

    private static Stream<Arguments> mapToProjectSet_shouldNotThrow_data() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(Collections.emptySet(), null),
                Arguments.of(null, Collections.emptySet()),
                Arguments.of(null, Collections.singleton(null)),
                Arguments.of(Collections.singleton(null), null),
                Arguments.of(Collections.singleton(null), Collections.singleton(null)),
                Arguments.of(Collections.emptySet(), Collections.emptySet()),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()), Collections.emptySet()),
                Arguments.of(Collections.emptySet(), Collections.singleton(new Project())),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()), Collections.singleton(new Project())),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()), Collections.singleton(getProjectWithRole(null))),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()),
                        Collections.singleton(getProjectWithRole(Collections.emptySet()))),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()),
                        Collections.singleton(getProjectWithRole(Collections.singleton(null)))),
                Arguments.of(Collections.singleton(ProjectDTO.builder().build()),
                        Collections.singleton(getProjectWithRole(Collections.singleton(new ProjectRole()))))
        );
    }

    private static Project getProjectWithRole(final Set<ProjectRole> projectRole) {
        final var project = new Project();

        project.setProjectRoles(projectRole);

        return project;
    }

    @ParameterizedTest
    @MethodSource("mapToProjectResponsesDTO_shouldNotThrow_data")
    void mapToProjectResponsesDTO_shouldNotThrow(final Set<Project> projects) {
        assertDoesNotThrow(() -> projectMapper.mapToProjectResponsesDTO(projects));
    }

    private static Stream<Set<Project>> mapToProjectResponsesDTO_shouldNotThrow_data() {
        return Stream.of(
                null,
                Collections.emptySet(),
                Collections.singleton(null),
                Collections.singleton(new Project())
        );
    }

}