package com.intive.patronative.dto;

import lombok.Value;

import java.util.Set;

@Value
public class ProjectsResponseDTO {
    Set<ProjectResponseDTO> projects;
}