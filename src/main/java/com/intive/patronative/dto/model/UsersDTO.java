package com.intive.patronative.dto.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UsersDTO {
    List<UserDTO> users;
}