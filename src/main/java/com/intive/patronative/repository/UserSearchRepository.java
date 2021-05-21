package com.intive.patronative.repository;

import com.intive.patronative.dto.UserSearchDTO;
import com.intive.patronative.repository.model.User;

import java.util.List;

public interface UserSearchRepository {
    List<User> findAllUsers(UserSearchDTO userSearchDTO);
}