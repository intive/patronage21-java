package com.intive.patronative.service;

import com.intive.patronative.dto.group.Groups;
import com.intive.patronative.model.Group;
import com.intive.patronative.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Groups getGroups() {
        log.info("Called getGroups");
        return new Groups(groupRepository.getAllGroups().stream()
                .map(Group::getName)
                .collect(Collectors.toList()));
    }
}
