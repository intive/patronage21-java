package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.ProjectRole;
import com.intive.patronative.repository.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RolesInProjectMapperTest {

    private final RolesInProjectMapper rolesInProjectMapper = new RolesInProjectMapper();

    @ParameterizedTest
    @MethodSource("mapToRolesInProjectSet_shouldNotThrow_data")
    void mapToRolesInProjectSet_shouldNotThrow(final User user, final Set<ProjectDTO> projectsDTO) {
        assertDoesNotThrow(() -> rolesInProjectMapper.mapToRolesInProjectSet(user, projectsDTO));
    }

    private static Stream<Arguments> mapToRolesInProjectSet_shouldNotThrow_data() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new User(), null),
                Arguments.of(null, Collections.emptySet()),
                Arguments.of(new User(), Collections.singleton(null)),
                Arguments.of(new User(), Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.emptySet()).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(null)).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(new Project())).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(getProject(null))).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(getProject(Collections.emptySet()))).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(getProject(Collections.singleton(null)))).build(),
                        Collections.singleton(ProjectDTO.builder().build())),
                Arguments.of(User.builder().projects(Collections.singleton(getProject(Collections.singleton(new ProjectRole())))).build(),
                        Collections.singleton(ProjectDTO.builder().build()))
        );
    }

    private static Project getProject(final Set<ProjectRole> projectRole) {
        final var project = new Project();

        project.setProjectRoles(projectRole);

        return project;
    }

}