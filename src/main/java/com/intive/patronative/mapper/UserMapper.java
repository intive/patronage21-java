package com.intive.patronative.mapper;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.User;

import java.util.Set;

public class UserMapper {

    public static User mapToEntity(final UserEditDTO userEditDTO, final User user, final Set<Project> availableProjects) {
        if (userEditDTO != null) {
            user.setFirstName(Mapper.swapValues(userEditDTO.getFirstName(), user.getFirstName()));
            user.setLastName(Mapper.swapValues(userEditDTO.getLastName(), user.getLastName()));
            user.setEmail(Mapper.swapValues(userEditDTO.getEmail(), user.getEmail()));
            user.setPhoneNumber(Mapper.swapValues(userEditDTO.getPhoneNumber(), user.getPhoneNumber()));
            user.setGitHubUrl(Mapper.swapValues(userEditDTO.getGitHubUrl(), user.getGitHubUrl()));
            user.getProfile().setBio(Mapper.swapValues(userEditDTO.getBio(), user.getProfile().getBio()));
            user.setProjects(ProjectMapper.toProjectSet(userEditDTO.getProjects(), availableProjects).orElse(user.getProjects()));
        }
        return user;
    }

}