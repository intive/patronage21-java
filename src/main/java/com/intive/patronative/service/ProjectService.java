package com.intive.patronative.service;

import com.intive.patronative.dto.ProjectsResponseDTO;
import com.intive.patronative.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectsResponseDTO getProjectsByYear(final Integer year) {
        return new ProjectsResponseDTO(projectRepository.getDistinctProjectNamesByYear(year != null ?
                year : Calendar.getInstance().get(Calendar.YEAR)));
    }

}