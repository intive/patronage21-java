package com.intive.patronative.service;

import org.mockito.Mockito;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.exception.UserNotFoundException;
import com.intive.patronative.exception.InvalidArgumentException;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.Profile;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.validation.UserSearchValidator;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    ProjectRepository projectRepository;
    @MockBean
    private UserSearchValidator userSearchValidator;
    @Autowired
    private UserService userService;

    @ParameterizedTest
    @MethodSource("validUserData")
    void updateUser_shouldNotThrow(final UserEditDTO userEditDTO) {
        Mockito.when(userRepository.findByLogin(userEditDTO.getLogin())).thenReturn(Optional.of(exampleUser()));
        Mockito.when(projectRepository.findAllByYear(Calendar.getInstance().get(Calendar.YEAR))).thenReturn(Set.of(exampleProject()));
        assertDoesNotThrow(() -> userService.updateUser(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("validProjectSet")
    void updateUser_shouldNotThrow(final Set<Project> projects) {
        Mockito.when(userRepository.findByLogin("login")).thenReturn(Optional.of(exampleUser()));
        Mockito.when(projectRepository.findAllByYear(Calendar.getInstance().get(Calendar.YEAR))).thenReturn(projects);
        assertDoesNotThrow(() -> userService.updateUser(new UserEditDTO("login", null, null, null, null, null, null,
                Collections.singleton(new ProjectDTO("exampleProjectName", "projectRole")))));
    }

    @ParameterizedTest
    @MethodSource("validUserData")
    void updateUser_shouldThrowUserNotFoundException(final UserEditDTO userEditDTO) {
        Mockito.when(userRepository.findByLogin(userEditDTO.getLogin())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidUserData")
    void updateUser_shouldThrowInvalidArgumentException(final UserEditDTO userEditDTO) {
        Mockito.when(userRepository.findByLogin(userEditDTO.getLogin())).thenReturn(Optional.of(new User()));
        assertThrows(InvalidArgumentException.class, () -> userService.updateUser(userEditDTO));
    }

    private static Stream<UserEditDTO> validUserData() {
        return Stream.of(
                new UserEditDTO("login", "firstName", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", Collections.singleton(new ProjectDTO("exampleProjectName", "projectRole"))),
                new UserEditDTO("login", "firstName", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", null),
                new UserEditDTO("login", null, null, null, null, null, "bio", null),
                new UserEditDTO("jKronoung", "Jacob", "Kronuung", "JackobK@mail.de", "987654321", "https://github.com/daJK",
                        "bio", Collections.singleton(new ProjectDTO("projectName", "projectRole")))
        );
    }

    private static Stream<UserEditDTO> invalidUserData() {
        return Stream.of(
                new UserEditDTO("FnLn", "first-Name", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", Collections.singleton(new ProjectDTO("projectName", "projectRole"))),
                new UserEditDTO("JacoKr", "Jaco Jr", "Kronon", "JacoKrono@mail.pl", "123 123 123", "https://github.com/JKda",
                        "bio", Collections.singleton(new ProjectDTO("projectName", "projectRole")))
        );
    }

    private User exampleUser() {
        final var user = new User();
        user.setLogin("exLogin");
        user.setFirstName("exFirstName");
        user.setLastName("exLastName");
        user.setEmail("exEmail");
        user.setPhoneNumber("exPhone");
        user.setGitHubUrl("exGithub");
        user.setProfile(new Profile());
        user.getProfile().setBio("exampleBio");
        user.setProjects(new HashSet<>());
        return user;
    }

    private static Project exampleProject() {
        final var project = new Project();
        project.setName("exampleProjectName");

        return project;
    }

    private static Stream<Set<Project>> validProjectSet() {
        return Stream.of(
                null,
                Collections.emptySet(),
                Set.of(exampleProject())
        );
    }

}