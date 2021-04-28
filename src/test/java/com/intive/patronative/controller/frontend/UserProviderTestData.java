package com.intive.patronative.controller.frontend;


import com.intive.patronative.dto.model.UserDTO;
import com.intive.patronative.dto.model.UsersDTO;

import java.util.List;

public class UserProviderTestData {

    public static UserDTO getUserDTO() {
        return new UserDTO(
                "AnnaNowak",
                "Anna",
                "Nowak",
                "anna.nowak@gmail.com",
                "222222222",
                "github/AnnaNowak"
        );
    }

    public static UsersDTO getOneElementUsersList() {
        return new UsersDTO(List.of(getUserDTO()));
    }
}