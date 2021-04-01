package com.intive.patronative.repository;

import com.intive.patronative.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
}
