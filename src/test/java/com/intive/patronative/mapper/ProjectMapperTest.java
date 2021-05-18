package com.intive.patronative.mapper;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.repository.model.Project;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectMapperTest {

    private final ProjectMapper projectMapper = new ProjectMapper();

    @ParameterizedTest
    @MethodSource("toProjectSet_dataCausingEmptyOptional")
    void toProjectSet_shouldReturnEmptyOptional(final Set<ProjectDTO> projectsDTO, final Set<Project> entityProjects) {
        assertEquals(Optional.empty(), projectMapper.mapToProjectSet(projectsDTO, entityProjects));
    }

    private static Stream<Arguments> toProjectSet_dataCausingEmptyOptional() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, Collections.emptySet()),
                Arguments.of(null, databaseProjectsSet()),
                Arguments.of(Collections.emptySet(), null),
                Arguments.of(Collections.emptySet(), Collections.emptySet())
        );
    }

    @ParameterizedTest
    @MethodSource("toProjectSet_expectedProjectSetAndGivenProjectDTOSet")
    void toProjectSet_shouldReturnProjectSet(final Set<Project> expected, final Set<ProjectDTO> given) {
        assertEquals(expected, projectMapper.mapToProjectSet(given, databaseProjectsSet()).orElseThrow());
    }

    private static Stream<Arguments> toProjectSet_expectedProjectSetAndGivenProjectDTOSet() {
        return Stream.of(
                Arguments.of(Collections.emptySet(), Collections.emptySet()),
                Arguments.of(getSet(Stream.of(getProject("Projekt II"))), // expected result
                        getSet(Stream.of(getProjectDTO(null), getProjectDTO("Projekt II")))), // given
                Arguments.of(getSet(Stream.of(getProject("Projekt I"), getProject("Projekt II"))),
                        getSet(Stream.of(getProjectDTO("Projekt I"), getProjectDTO("Projekt II")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III"))),
                        getSet(Stream.of(getProjectDTO("Projekt I"), getProjectDTO("Projekt II"), getProjectDTO("Projekt III")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III"))),
                        getSet(Stream.of(getProjectDTO("Projekt I"), getProjectDTO("Projekt II"), getProjectDTO("Projekt III"), getProjectDTO("Projekt IV")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt I"), getProject("Projekt III"))),
                        getSet(Stream.of(getProjectDTO("Projekt I"), getProjectDTO("Projekt III")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt II"), getProject("Projekt III"))),
                        getSet(Stream.of(getProjectDTO("Projekt II"), getProjectDTO("Projekt III")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt II"), getProject("Projekt I"))),
                        getSet(Stream.of(getProjectDTO("Projekt II"), getProjectDTO("Projekt I")))),
                Arguments.of(getSet(Stream.of(getProject("Projekt II"), getProject("Projekt I"))),
                        getSet(Stream.of(getProjectDTO("Projekt II"), getProjectDTO("Projekt I"), getProjectDTO("Projekt I"))))
        );
    }

    @ParameterizedTest
    @MethodSource("mapToProjectDTOList_expectedProjectDTOListAndGivenProjectSet")
    void mapToProjectDTOList_shouldReturnNullOrProjectDTOList(final List<ProjectDTO> expected, final Set<Project> given) {
        assertEquals(expected, projectMapper.mapToProjectDTOList(given));
    }

    private static Stream<Arguments> mapToProjectDTOList_expectedProjectDTOListAndGivenProjectSet() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(Collections.singletonList(getProjectDTO(null)), getSet(Stream.of(new Project()))),
                Arguments.of(Collections.singletonList(getProjectDTO("example")), getSet(Stream.of(getProject("example"))))
        );
    }

    @ParameterizedTest
    @MethodSource("mapToProjectDTO_expectedProjectDTOAndGivenProject")
    void mapToProjectDTO_shouldReturnNullOrProjectDTO(final ProjectDTO expected, final Project given) {
        assertEquals(expected, projectMapper.mapToProjectDTO(given));
    }

    private static Stream<Arguments> mapToProjectDTO_expectedProjectDTOAndGivenProject() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(getProjectDTO(null), getProject(null)),
                Arguments.of(getProjectDTO("example"), getProject("example"))
        );
    }

    private static ProjectDTO getProjectDTO(final String name) {
        return ProjectDTO.builder().name(name).role(null).build();
    }

    private static Project getProject(final String name) {
        final var project = new Project();
        project.setName(name);
        project.setYear(Calendar.getInstance().get(Calendar.YEAR));
        return project;
    }

    private static Set<Object> getSet(Stream<Object> stream) {
        return stream.collect(Collectors.toSet());
    }

    private static Set<Project> databaseProjectsSet() {
        return new HashSet<>(Arrays.asList(getProject("Projekt I"), getProject("Projekt II"), getProject("Projekt III")));
    }

}