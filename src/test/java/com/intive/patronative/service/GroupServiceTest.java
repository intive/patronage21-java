package com.intive.patronative.service;

import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.model.Group;
import com.intive.patronative.repository.GroupRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
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
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupService groupService;

    @ParameterizedTest
    @MethodSource("provideGroupNames")
    public void getGroups_shouldReturnGroupsFromRepository(List<String> groupNames) {
        Group[] testGroups = groupNames.stream().map(String::valueOf).map(Group::new).toArray(Group[]::new);

        when(groupRepository.getAllGroups()).thenReturn(List.of(testGroups));
        final var groups = groupService.getGroups();

        assertThat(groups).isEqualTo(new Groups(groupNames));
    }

    private static Stream provideGroupNames() {
        return Stream.of(
                List.of("Java", "QA"),
                List.of("Android", "Embedded"),
                List.of("Android", "Embedded", "Java", "QA")
        );
    }
}