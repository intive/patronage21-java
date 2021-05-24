package com.intive.patronative.controller.frontend;


import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;
import com.intive.patronative.dto.profile.UserStatus;

import java.util.List;

public class UserProviderTestData {

    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .login("AnnaNowak")
                .firstName("Anna")
                .lastName( "Nowak")
                .image(null)
                .status(UserStatus.ACTIVE)
                .build();
    }

    public static UsersDTO getOneElementUsersList() {
        return new UsersDTO(List.of(getUserDTO()));
    }
}