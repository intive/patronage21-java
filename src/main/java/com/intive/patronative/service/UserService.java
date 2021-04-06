package com.intive.patronative.service;

import com.intive.patronative.dto.profile.User;
import com.intive.patronative.dto.profile.UserDto;
import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void update(final User user) {
        log.info("Evoking service...");
    }

    public List<UserDto> getUserByRole(UserRole userRole) {
        return userRepository.getAllUsers().stream()
                .filter(role -> role.getRole().equals(userRole))
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.<UserDto>toList());
    }
}
