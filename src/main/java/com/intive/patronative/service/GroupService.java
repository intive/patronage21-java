package com.intive.patronative.service;

import com.intive.patronative.dto.model.GroupsDTO;
import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.exception.TechnologyGroupNotFoundException;
import com.intive.patronative.repository.TechnologyGroupRepository;
import com.intive.patronative.repository.model.TechnologyGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {

    private final TechnologyGroupRepository groupRepository;

    public GroupsDTO getGroups() {
        log.info("Called getGroups");
        final var groups = groupRepository.findAll().stream()
                .map(TechnologyGroup::getName)
                .collect(Collectors.toList());
        return new GroupsDTO(groups);
    }

    public Set<TechnologyGroup> getTechnologyGroupsEntitiesByList(final Set<TechnologyGroupDTO> technologyGroupsRequest) {
        return Optional.ofNullable(technologyGroupsRequest)
                .map(technologyGroups -> technologyGroups.stream()
                        .filter(technologyGroup -> technologyGroup.getName() != null)
                        .map(technologyGroup -> groupRepository.findByName(technologyGroup.getName())
                                .orElseThrow(() -> new TechnologyGroupNotFoundException(technologyGroup.getName())))
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
}
