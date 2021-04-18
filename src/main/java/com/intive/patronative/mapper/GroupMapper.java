package com.intive.patronative.mapper;

import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.repository.model.TechnologyGroup;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public TechnologyGroupDTO toResponse(final TechnologyGroup technologyGroupEntity) {
        return Optional.ofNullable(technologyGroupEntity)
                .map(technologyGroup -> new TechnologyGroupDTO(technologyGroupEntity.getName()))
                .orElse(null);
    }

    public Set<TechnologyGroupDTO> toResponseList(final Set<TechnologyGroup> technologyGroupsEntities) {
        return Optional.ofNullable(technologyGroupsEntities)
                .map(technologyGroups -> technologyGroups.stream()
                        .map(this::toResponse)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet()))
                .orElse(null);
    }
}
