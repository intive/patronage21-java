package com.intive.patronative.mapper;

import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.repository.model.TechnologyGroup;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public TechnologyGroupDTO toResponse(final TechnologyGroup technologyGroup) {
        if (technologyGroup == null) {
            return null;
        }
        return new TechnologyGroupDTO(technologyGroup.getName());
    }

    public List<TechnologyGroupDTO> toResponse(final Set<TechnologyGroup> technologyGroups) {
        if (technologyGroups == null) {
            return Collections.emptyList();
        }
        return technologyGroups
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
