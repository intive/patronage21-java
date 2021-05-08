package com.intive.patronative.service;

import com.intive.patronative.dto.ProjectRolesDTO;
import com.intive.patronative.repository.ProjectRoleRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRoleRepository projectRoleRepository;
    @InjectMocks
    private ProjectService projectService;

    @ParameterizedTest
    @MethodSource("provideRepositoryProjectRolesAndExpectedProjectRoles")
    public void shouldReturnProjectRolesFromRepository(final Set<String> projectRoles, final Set<String> expectedProjectRoles, final BigDecimal id) {
        // given
        when(projectRoleRepository.getRolesByProject(id)).thenReturn(projectRoles);

        // when
        final var projectRolesDTO = projectService.getRolesByProject(id);

        // then
        assertThat(projectRolesDTO).isEqualTo(new ProjectRolesDTO(expectedProjectRoles));
    }

    private static Stream<Arguments> provideRepositoryProjectRolesAndExpectedProjectRoles() {
        return Stream.of(
                Arguments.of(Set.of("Scrum Master", "Tester"), Set.of("Scrum Master", "Tester"), BigDecimal.valueOf(1)),
                Arguments.of(Set.of("Project Manager", "Developer", "Tester"), Set.of("Project Manager", "Developer", "Tester"), BigDecimal.valueOf(21)),
                Arguments.of(Set.of(), Set.of(), BigDecimal.valueOf(11))
        );
    }
}