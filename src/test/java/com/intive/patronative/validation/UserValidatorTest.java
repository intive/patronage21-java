package com.intive.patronative.validation;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @ParameterizedTest
    @MethodSource("validUserData")
    void validateUserData_shouldNotThrow(final UserEditDTO userEditDTO) {
        assertDoesNotThrow(() -> new UserValidator().validateUserData(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidUserData")
    void validateUserData_shouldThrowInvalidArgumentException(final UserEditDTO userEditDTO) {
        assertThrows(InvalidArgumentException.class, () -> new UserValidator().validateUserData(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("validFirstNames")
    void isFirstNameValid_shouldReturnTrue(final String firstName) {
        assertTrue(UserValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("invalidFirstNames")
    void isFirstNameValid_shouldReturnFalse(final String firstName) {
        assertFalse(UserValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("validLastNames")
    void isLastNameValid_shouldReturnTrue(final String lastName) {
        assertTrue(UserValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("invalidLastNames")
    void isLastNameValid_shouldReturnFalse(final String lastName) {
        assertFalse(UserValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("validUsernames")
    void isUsernameValid_shouldReturnTrue(final String username) {
        assertTrue(UserValidator.isUsernameValid(username));
    }

    @ParameterizedTest
    @MethodSource("invalidUsernames")
    void isUsernameValid_shouldReturnFalse(final String username) {
        assertFalse(UserValidator.isUsernameValid(username));
    }

    @ParameterizedTest
    @MethodSource("validEmails")
    void isEmailValid_shouldReturnTrue(final String email) {
        assertTrue(UserValidator.isEmailValid(email));
    }

    @ParameterizedTest
    @MethodSource("invalidEmails")
    void isEmailValid_shouldReturnFalse(final String email) {
        assertFalse(UserValidator.isEmailValid(email));
    }

    @ParameterizedTest
    @MethodSource("validPhones")
    void isPhoneValid_shouldReturnTrue(final String phone) {
        assertTrue(UserValidator.isPhoneValid(phone));
    }

    @ParameterizedTest
    @MethodSource("invalidPhones")
    void isPhoneValid_shouldReturnFalse(final String phone) {
        assertFalse(UserValidator.isPhoneValid(phone));
    }

    @ParameterizedTest
    @MethodSource("validGithub")
    void isGithubValid_shouldReturnTrue(final String github) {
        assertTrue(UserValidator.isGithubValid(github));
    }

    @ParameterizedTest
    @MethodSource("invalidGithub")
    void isGithubValid_shouldReturnFalse(final String github) {
        assertFalse(UserValidator.isGithubValid(github));
    }

    @ParameterizedTest
    @MethodSource("validBio")
    void isBioValid_shouldReturnTrue(final String bio) {
        assertTrue(UserValidator.isBioValid(bio));
    }

    @ParameterizedTest
    @MethodSource("invalidBio")
    void isBioValid_shouldReturnFalse(final String bio) {
        assertFalse(UserValidator.isBioValid(bio));
    }

    private static Stream<String> validFirstNames() {
        return Stream.of("lucas", "Lucas", "łukasz", "Łukasz", "MatEuSZ", "Arabella", "OL");
    }

    private static Stream<String> invalidFirstNames() {
        return Stream.of(null, "lucas ", " Lucas", "łuk asz", "Łuk-asz", "MatE.uSZ", "*Marek", "Szym0n", "{Sometext}", "-Mark",
                "/Henk", "#home", "");
    }

    private static Stream<String> validLastNames() {
        return Stream.of("Zukerberg", "Jobs", "Gąsienica-Makowski", "Gąsienica Makowski", "Gąsior", "markowski", "Curuś");
    }

    private static Stream<String> invalidLastNames() {
        return Stream.of(null,"Zuke*rberg", "J+obs", "Gąsienica-", "-Gąsienica-", "-Gąsienica", "Gąsienica ", " Gąsienica ",
                " Gąsienica", "Gąsi$r", "markowsk@", "Curu1", "Cu- ru", "");
    }

    private static Stream<String> validUsernames() {
        return Stream.of("lucas", "lucas123", "123lucas", "luc123as", "Lucas", "nameSurname");
    }

    private static Stream<String> invalidUsernames() {
        return Stream.of(null, "Luca-", "Hi Mark", "123-patrick", "Bob*the*builder", "-masha", "the+Bear");
    }

    private static Stream<String> validEmails() {
        return Stream.of("lucasSmith@mail.pl", "SMITHLUCAS@emailo.com", "lucassmith123@gmail.uk", "expresive.mail@mail.pl");
    }

    private static Stream<String> invalidEmails() {
        return Stream.of(null, "lucas-Smith@mail.pl", "SMIT HLUCAS@emailo.com", "lucassmith123@gmail.u", "expresive.mail@mail.polska",
                "lucasSmith+mail.pl", "SMITHLUCAS@emailocom", "lucassmith123@.uk", "expresive.mail@mail.PL", "ęxpresive.mail@mail.pl");
    }

    private static Stream<String> validPhones() {
        return Stream.of("123456789", "0123456789", "48123456789");
    }

    private static Stream<String> invalidPhones() {
        return Stream.of(null, "123 456 789", "01 23456789", "48 123 456 789", "48-123-456-789", "48 4878912", "1234567");
    }

    private static Stream<String> validGithub() {
        return Stream.of("https://github.com/username1234", "https://github.com/username", "https://github.com/1234",
                "https://github.com/USERNAME", "https://github.com/user-name", "https://github.com/patronage-java-2021",
                "https://github.com/user", "https://github.com/1234", "https://github.com/luca-12");
    }

    private static Stream<String> invalidGithub() {
        return Stream.of(null, "https://github.com/ósername", "https://github.com/user_name", "username", "https://github.com/use",
                "https://github.com/-user-name", "https://github.com/user-name-", "https://github.com/-user-name-");
    }

    private static Stream<String> validBio() {
        return Stream.of("Some valid bio");
    }

    private static Stream<String> invalidBio() {
        return Stream.of(
                null,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque porta lorem quis tristique tempus. Morbi sit amet dui nulla. Phasellus nec euismod diam. Curabitur at finibus orci. Mauris eu ultricies tortor. Ut vitae tempor nibh. Proin sit amet nunc condimentum, ultricies leo in, laoreet dolor. Maecenas ut erat sit amet lectus venenatis semper ac vel ante. Suspendisse eros felis, dapibus sed mauris a, scelerisque mollis turpis. Nullam vestibulum tellus nunc, vitae eleifend nisi porta non. Integer libero est, aliquam tempus nisi ut, porta mollis turpis. Nulla facilisi. Phasellus nec nisi erat. Quisque ac sodales quam, ut rhoncus neque. Donec euismod, orci vitae molestie finibus, tellus elit consectetur arcu, sed malesuada nisl nibh eget velit. Pellentesque laoreet pellentesque finibus.\n" +
                "\n" +
                "Etiam commodo a turpis ac pretium. Phasellus placerat sapien ante, ut rhoncus justo tempor sodales. Quisque ac fermentum felis. Donec ac varius nunc. Praesent quis vulputate neque. Nulla vestibulum viverra rutrum. Proin a quam placerat, commodo ante nec, placerat augue. Praesent sodales, tellus eu volutpat posuere, lorem eros ultricies mauris, et eleifend quam lacus eget erat. Duis velit augue, euismod id lacus at, molestie bibendum ex.\n" +
                "\n" +
                "Nunc quis rutrum orci, imperdiet ultricies risus. Sed accumsan magna semper erat convallis efficitur. Etiam rhoncus, tortor vitae fermentum convallis, sem lectus cursus sapien, accumsan lobortis tellus eros non nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Quisque tempus magna eget justo volutpat, ac placerat turpis hendrerit. Nam rhoncus dapibus auctor. Donec venenatis tristique erat tempor laoreet. Mauris at felis libero. Ut felis augue, tristique quis erat a, accumsan molestie risus. Suspendisse id elit quis diam sagittis consectetur sit amet vel elit. Etiam ut sollicitudin purus, a facilisis nulla. Integer vitae eros dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse potenti.\n" +
                "\n" +
                "Nullam tristique faucibus ligula. Nunc cursus arcu ac dolor efficitur fermentum. Nullam gravida luctus mi non porta. Maecenas vel neque metus. Nullam mollis, metus vitae cursus dapibus, nisi lorem tincidunt velit, non dignissim tortor lectus in felis. Sed vel urna eu odio bibendum viverra. Aliquam dictum scelerisque arcu. Praesent consequat, leo vitae porttitor aliquam, sem purus iaculis tellus, eu efficitur felis arcu id massa.\n" +
                "\n" +
                "Nunc pellentesque sem nibh. Praesent a erat eros. Vivamus eget est dignissim, efficitur ipsum ac, laoreet sapien. Mauris risus sapien, ultrices vel leo sed, mattis tempor est. Curabitur a eleifend mauris, a consequat urna. Suspendisse mattis aliquet libero et blandit. Vestibulum rhoncus felis lorem, et venenatis erat feugiat non. Integer quis eros malesuada erat dapibus lacinia. Donec imperdiet sapien a magna elementum gravida. Aliquam lobortis quam auctor ligula vestibulum convallis. Suspendisse id suscipit mi. Sed iaculis dui eget congue tincidunt. Nunc ut sagittis purus. Maecenas ac metus mauris.\n" +
                "\n" +
                "Vestibulum vel tincidunt tellus. Maecenas porttitor quis lorem eget consectetur. Mauris laoreet odio est, sed malesuada diam faucibus eget. Nulla sem mi, volutpat quis vehicula vel, rhoncus eget mauris. Vestibulum convallis consectetur orci et rhoncus. In et porta lacus, sed convallis nunc. Phasellus vel nunc pellentesque nibh porttitor accumsan sed quis turpis. Donec elementum interdum gravida. In odio quam, ultrices laoreet justo sed."
        );
    }

    private static Stream<UserEditDTO> validUserData() {
        return Stream.of(
                new UserEditDTO(null, "Lucas", "Smith", "lucasSmith@mail.pl", "123456789", "https://github.com/username1234",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", null, null, null, null, null, null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mike", "Wazofski", "m.wasoff@minc.mc", "1123456789", "https://github.com/leeBronzJamez",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker", "ZuckBerk@fb.com", "918273645", null, null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker", "Zuck.Berk@fb.com", "918273645", null, "username102",
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO(null, "łukasz", "śmith-zerga", null, "48123456789", "https://github.com/user-name-1234",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build()))
        );
    }

    private static Stream<UserEditDTO> invalidUserData() {
        return Stream.of(
                new UserEditDTO(null, "Lucas1", "Smith", "lucasSmith@mail.pl", "123456789", "https://github.com/username1234",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", null, null, null, "123456", "https://github.com/-username1234-", null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "-Zucker-Manen-", "ZuckBerk@fb.com", "918273645123", null, null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker-Manen", "ZuckBerk@fb.com", "918273645123", "justUsername",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker", "Zuck-Berk@fb.com", "918273645", null, "username-102",
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO(null, "łukasz", "śmith-zerga", null, "48123456789", "https://github.com/-user-name-1234",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build()))
        );
    }

}