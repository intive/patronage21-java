package com.intive.patronative.service;

import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.validation.UserValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.Calendar;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    ProjectRepository projectRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private UserSearchValidator userSearchValidator;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
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
                Collections.singleton(ProjectDTO.builder().name("exampleProjectName").role("projectRole").build()))));
    }

    @ParameterizedTest
    @MethodSource("validUserData")
    void updateUser_shouldThrowUserNotFoundException(final UserEditDTO userEditDTO) {
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidUserData")
    void updateUser_shouldThrowInvalidArgumentException(final UserEditDTO userEditDTO) {
        Mockito.doThrow(InvalidArgumentException.class).when(userValidator).validateUserData(userEditDTO);
        assertThrows(InvalidArgumentException.class, () -> userService.updateUser(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("validLogin")
    void getUser_shouldNotThrow(final String login) {
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.of(exampleUser()));
        assertDoesNotThrow(() -> userService.getUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("validLogin")
    void getUser_shouldThrowUserNotFoundException(final String login) {
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("invalidLogin")
    void getUser_shouldThrowInvalidArgumentException(final String login) {
        assertThrows(InvalidArgumentException.class, () -> userService.getUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("validLogin")
    void deactivateUser_shouldNotThrow(final String login) {
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.of(new User()));
        assertDoesNotThrow(() -> userService.deactivateUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("validLogin")
    void deactivateUser_shouldThrowUserNotFoundException(final String login) {
        Mockito.when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deactivateUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("invalidLogin")
    void deactivateUser_shouldInvalidArgumentException(final String login) {
        assertThrows(InvalidArgumentException.class, () -> userService.deactivateUserByLogin(login));
    }

    @ParameterizedTest
    @MethodSource("usersFromDatabase")
    void deleteImage_shouldNotThrow(final User user) {
        // when
        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        // then
        assertDoesNotThrow(() -> userService.deleteImage("AnnaNowak"));
    }

    private static Stream<UserEditDTO> validUserData() {
        return Stream.of(
                new UserEditDTO("login", "firstName", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", Collections.singleton(ProjectDTO.builder().name("exampleProjectName").role("projectRole").build())),
                new UserEditDTO("login", "firstName", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", null),
                new UserEditDTO("login", null, null, null, null, null, "bio", null),
                new UserEditDTO("jKronoung", "Jacob", "Kronuung", "JackobK@mail.de", "987654321", "https://github.com/daJK",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build()))
        );
    }

    private static Stream<UserEditDTO> invalidUserData() {
        return Stream.of(
                new UserEditDTO("FnLn", "first-Name", "lastName", "email@mail.pl", "123456789", "https://github.com/username",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("JacoKr", "Jaco Jr", "Kronon", "JacoKrono@mail.pl", "123 123 123", "https://github.com/JKda",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build()))
        );
    }

    private static Stream<String> invalidLogin() {
        return Stream.of(null, "l", "Luc-Skywalker", ".dot");
    }

    private static Stream<String> validLogin() {
        return Stream.of("AnnaNowak", "ValidLogin123");
    }

    private static Stream<User> usersFromDatabase() {
        return Stream.of(
                User.builder().profile(null).build(),
                User.builder().profile(new Profile()).build()
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