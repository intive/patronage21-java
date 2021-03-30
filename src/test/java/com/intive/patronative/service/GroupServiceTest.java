package com.intive.patronative.service;

import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.model.Group;
import com.intive.patronative.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
    @Mock
    private GroupRepository groupRepository;
    @InjectMocks
    private GroupService groupService;

    @Test
    public void getGroups_shouldReturnGroupsFromRepository() {
        when(groupRepository.getAllGroups()).thenReturn(List.of(
                new Group("Android"),
                new Group("Embedded")));
        var groups = groupService.getGroups();
        assertThat(groups).isEqualTo(new Groups(List.of("Android", "Embedded")));
    }
}