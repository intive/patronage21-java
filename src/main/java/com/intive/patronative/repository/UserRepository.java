package com.intive.patronative.repository;

import com.intive.patronative.dto.profile.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
}
