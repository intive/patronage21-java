package com.intive.patronative.service;

import com.intive.patronative.dto.model.GroupsDTO;
import com.intive.patronative.repository.TechnologyGroupRepository;
import com.intive.patronative.repository.model.TechnologyGroup;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private TechnologyGroupRepository groupRepository;
    @InjectMocks
    private GroupService groupService;

    @ParameterizedTest
    @MethodSource("provideRepositoryGroupsAndExpectedGroups")
    public void shouldReturnGroupsFromRepository(final List<TechnologyGroup> groups, final List<String> expectedGroups) {
        when(groupRepository.findAll()).thenReturn(groups);
        final var groupsDTO = groupService.getGroups();
        assertThat(groupsDTO).isEqualTo(new GroupsDTO(expectedGroups));
    }

    private static Stream<Arguments> provideRepositoryGroupsAndExpectedGroups() {
        return Stream.of(
                Arguments.of(List.of(createGroup("Android"), createGroup("Embedded")), List.of("Android", "Embedded")),
                Arguments.of(List.of(createGroup("Java"), createGroup("QA")), List.of("Java", "QA")),
                Arguments.of(List.of(createGroup("Java"), createGroup("QA"), createGroup("Android"), createGroup("Embedded")),
                        List.of("Java", "QA", "Android", "Embedded")),
                Arguments.of(List.of(), List.of())
        );
    }

    private static TechnologyGroup createGroup(final String name) {
        final var group = new TechnologyGroup();
        group.setName(name);
        return group;
    }
}