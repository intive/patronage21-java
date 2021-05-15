package com.intive.patronative.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectDTO {
    String name;
    String role;
}