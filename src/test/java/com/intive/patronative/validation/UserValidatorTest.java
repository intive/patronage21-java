package com.intive.patronative.validation;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = {ValidationHelper.class, UserValidator.class})
class UserValidatorTest {

    @Autowired
    private UserValidator userValidator;

    @ParameterizedTest
    @MethodSource("validUserData")
    void validateUserData_shouldNotThrow(final UserEditDTO userEditDTO) {
        assertDoesNotThrow(() -> userValidator.validateUserData(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("invalidUserData")
    void validateUserData_shouldThrowInvalidArgumentException(final UserEditDTO userEditDTO) {
        assertThrows(InvalidArgumentException.class, () -> userValidator.validateUserData(userEditDTO));
    }

    @ParameterizedTest
    @MethodSource("validFirstNames")
    void isFirstNameValid_shouldReturnTrue(final String firstName) {
        assertTrue(userValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("invalidFirstNames")
    void isFirstNameValid_shouldReturnFalse(final String firstName) {
        assertFalse(userValidator.isFirstNameValid(firstName));
    }

    @ParameterizedTest
    @MethodSource("validLastNames")
    void isLastNameValid_shouldReturnTrue(final String lastName) {
        assertTrue(userValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("invalidLastNames")
    void isLastNameValid_shouldReturnFalse(final String lastName) {
        assertFalse(userValidator.isLastNameValid(lastName));
    }

    @ParameterizedTest
    @MethodSource("validLogin")
    void isLoginValid_shouldReturnTrue(final String login) {
        assertTrue(userValidator.isLoginValid(login));
    }

    @ParameterizedTest
    @MethodSource("invalidLogin")
    void isLoginValid_shouldReturnFalse(final String login) {
        assertFalse(userValidator.isLoginValid(login));
    }

    @ParameterizedTest
    @MethodSource("validImage")
    void isImageValid_shouldReturnTrue(final MultipartFile image) {
        //when
        final var imageValid = UserValidator.isImageValid(image);

        //then
        assertTrue(imageValid);
    }

    @ParameterizedTest
    @MethodSource("invalidImage")
    void isImageValid_shouldReturnFalse(final MultipartFile image) {
        //when
        final var imageValid = UserValidator.isImageValid(image);

        //then
        assertFalse(imageValid);
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
        return Stream.of(null, "Zuke*rberg", "J+obs", "Gąsienica-", "-Gąsienica-", "-Gąsienica", "Gąsienica ", " Gąsienica ",
                " Gąsienica", "Gąsi$r", "markowsk@", "Curu1", "Cu- ru", "");
    }

    private static Stream<String> validLogin() {
        return Stream.of("lucas", "lucas123", "123lucas", "luc123as", "Lucas", "nameSurname");
    }

    private static Stream<String> invalidLogin() {
        return Stream.of(null, "Luca-", "Hi Mark", "123-patrick", "Bob*the*builder", "-masha", "the+Bear");
    }

    private static Stream<UserEditDTO> validUserData() {
        return Stream.of(
                new UserEditDTO(null, null, null, null, null, null, null, null),
                new UserEditDTO("lucas", null, null, null, null, null, null, null),
                new UserEditDTO("lucas123", null, null, null, null, null, null, null),
                new UserEditDTO("Lucas", null, null, null, null, null, null, null),
                new UserEditDTO("OhHiMark", null, null, null, null, null, null, null),
                new UserEditDTO(null, null, null, "lucasSmith@mail.pl", null, null, null, null),
                new UserEditDTO(null, null, null, "SMITHLUCAS@emailo.com", null, null, null, null),
                new UserEditDTO(null, null, null, "lucassmith123@gmail.uk", null, null, null, null),
                new UserEditDTO(null, null, null, "expresive.mail@mail.pl", null, null, null, null),
                new UserEditDTO(null, null, null, null, "123456789", null, null, null),
                new UserEditDTO(null, null, null, null, null, "github.com/luca-12", null, null),
                new UserEditDTO(null, null, null, null, null, "www.github.com/user", null, null),
                new UserEditDTO(null, null, null, null, null, "http://github.com/1234", null, null),
                new UserEditDTO(null, null, null, null, null, "https://www.github.com/user-name", null, null),
                new UserEditDTO(null, null, null, null, null, "http://www.github.com/patronage-java-2021", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/USERNAME", null, null),
                new UserEditDTO(null, "Lucas", "Smith", null, null, "https://github.com/username1234",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", null, null, null, null, null, null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mike", "Wazofski", "m.wasoff@minc.mc", "123456789", "https://www.github.com/leeBronzJamez",
                        null, Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker", "ZuckBerk@fb.com", "918273645", null, null,
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO("SomeLogin", "Mark", "Zucker", "Zuck.Berk@fb.com", "918273645", null, "username102",
                        Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build())),
                new UserEditDTO(null, "łukasz", "śmith-zerga", null, "123456789", "https://github.com/user-name-1234",
                        "bio", Collections.singleton(ProjectDTO.builder().name("projectName").role("projectRole").build()))
        );
    }

    private static Stream<UserEditDTO> invalidUserData() {
        return Stream.of(
                new UserEditDTO(null, null, null, "lucassmith123@.uk", null, null, null, null),
                new UserEditDTO(null, null, null, "ęxpresive.mail@mail.pl", null, null, null, null),
                new UserEditDTO(null, null, null, "expresive.mail@mail.PL", null, null, null, null),
                new UserEditDTO(null, null, null, "SMITHLUCAS@emailocom", null, null, null, null),
                new UserEditDTO(null, null, null, "lucasSmith+mail.pl", null, null, null, null),
                new UserEditDTO(null, null, null, "lucas-Smith@mail.pl", null, null, null, null),
                new UserEditDTO(null, null, null, "SMIT HLUCAS@emailo.com", null, null, null, null),
                new UserEditDTO(null, null, null, "expresive.mail@mail.polska", null, null, null, null),
                new UserEditDTO(null, null, null, null, "123 456 789", null, null, null),
                new UserEditDTO(null, null, null, null, "01 23456789", null, null, null),
                new UserEditDTO(null, null, null, null, "48-123-456-789", null, null, null),
                new UserEditDTO(null, null, null, null, "0123456789", null, null, null),
                new UserEditDTO(null, null, null, null, "1234567", null, null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/-user-name-", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/user-name-", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/-user-name", null, null),
                new UserEditDTO(null, null, null, null, null, "username", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/ósername", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/user_name", null, null),
                new UserEditDTO(null, null, null, null, null, "https://github.com/use", null, null),
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

    private static Stream<MultipartFile> validImage() {
        return Stream.of(
                createMultiPartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE),
                createMultiPartFile("image", "image.jpeg", MediaType.IMAGE_JPEG_VALUE),
                createMultiPartFile("photo", "photo.png", MediaType.IMAGE_PNG_VALUE),
                createMultiPartFile("photo", "photo.txt", MediaType.IMAGE_PNG_VALUE),
                createMultiPartFile("PHOTO", "PHOTO.PNG", MediaType.IMAGE_PNG_VALUE),
                createMultiPartFile("picture", "logo.gif", MediaType.IMAGE_GIF_VALUE)
        );
    }

    private static Stream<MultipartFile> invalidImage() {
        return Stream.of(
                createMultiPartFile("image", "image.drawio", MediaType.APPLICATION_CBOR_VALUE),
                createMultiPartFile("image", "image.jpeg", MediaType.APPLICATION_PDF_VALUE),
                createMultiPartFile("photo", "photo.png", MediaType.APPLICATION_XML_VALUE),
                createMultiPartFile("photo", "photo.txt", MediaType.TEXT_PLAIN_VALUE),
                null
        );
    }

    private static MultipartFile createMultiPartFile(final String name, final String originalName, final String mediaType) {
        return new MockMultipartFile(
                name,
                originalName,
                mediaType,
                new byte[1024 * 60]);
    }

}