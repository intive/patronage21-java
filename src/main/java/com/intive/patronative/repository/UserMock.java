package com.intive.patronative.repository;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMock implements UserRepository {

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("Tomasz", "Wisniewski", "abc@gmail.com", "123456789",
                "gitURL.git", "user1", UserRole.CANDIDATE, UserStatus.ACTIVE));
        users.add(new User("Jan", "Kowalski", "def@gmail.com", "111111111",
                "gitURL2.git", "user2", UserRole.LEADER, UserStatus.ACTIVE));
        users.add(new User("Aleksandra", "Nowak", "ghi@gmail.com", "999999999",
                "gitURL3.git", "user3", UserRole.CANDIDATE, UserStatus.ACTIVE));
        return users;
    }
}
