package com.intive.patronative.validation;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserValidator {

    @Value("${validators.user.projects.maximum-participation}")
    private int projectsParticipationLimit;

    private static final int BIO_LENGTH = 512;
    private static final int GITHUB_LENGTH = 256;
    private static final int MIN_GITHUB_USERNAME_LENGTH = 4;
    private static final String BASE_GITHUB_LINK = "https://github.com/";
    private static final Pattern FIRST_NAME_PATTERN = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{2,64}$");
    private static final Pattern LAST_NAME_PATTERN = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{2,31}[- ]?[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{2,31}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{2,32}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.]{4,45}+[@][a-zA-Z]{2,10}[.][a-z]{2,5}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9,16}$");
    private static final Pattern GITHUB_PATTERN = Pattern.compile("^" + BASE_GITHUB_LINK + "[a-zA-Z0-9]+([-?][a-zA-Z0-9]+)*$");

    private static final String FIRST_NAME_MESSAGE = "Only letters, 2 to 64 letters";
    private static final String LAST_NAME_MESSAGE = "Only letters, dash or space allowed for two part surnames, each surname 2 to 32 letters";
    private static final String EMAIL_MESSAGE = "Example e-mail: example.Mail123@mail.com";
    private static final String PHONE_MESSAGE = "9 to 16 numbers";
    private static final String GITHUB_MESSAGE = "Letters, numbers and dashes allowed, username minimum 4 characters, " +
            "has to start with https://github.com/, username cannot start or end with dash";
    private static final String BIO_MESSAGE = "Bio up to 512 characters";
    private static final String PROJECT_MAX_PARTICIPATION_MESSAGE = "Maximum number of projects in which you can participate is: ";

    public void validateUserData(final UserEditDTO userDTO) {
        final var fieldErrors = getFieldErrors(userDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    public static boolean isFirstNameValid(final String firstName) {
        return (firstName != null) && FIRST_NAME_PATTERN.matcher(firstName).matches();
    }

    public static boolean isLastNameValid(final String lastName) {
        return (lastName != null) && LAST_NAME_PATTERN.matcher(lastName).matches();
    }

    public static boolean isUsernameValid(final String username) {
        return (username != null) && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isEmailValid(final String email) {
        return (email != null) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPhoneValid(final String phone) {
        return (phone != null) && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isGithubValid(final String github) {
        return (github != null) && ((GITHUB_PATTERN.matcher(github).matches()) && (github.length() <= GITHUB_LENGTH)
                && (github.length() >= (BASE_GITHUB_LINK.length() + MIN_GITHUB_USERNAME_LENGTH)));
    }

    public static boolean isBioValid(final String bio) {
        return (bio != null) && (bio.length() <= BIO_LENGTH);
    }

    private List<FieldError> getFieldErrors(final UserEditDTO userEditDTO) {
        if (userEditDTO != null) {
            return Stream.of(checkFirstName(userEditDTO.getFirstName()),
                    checkLastName(userEditDTO.getLastName()),
                    checkEmail(userEditDTO.getEmail()),
                    checkPhone(userEditDTO.getPhoneNumber()),
                    checkGithub(userEditDTO.getGitHubUrl()),
                    checkBio(userEditDTO.getBio()),
                    checkProjects(userEditDTO.getProjects()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private FieldError checkFirstName(final String firstName) {
        return (firstName == null) || (isFirstNameValid(firstName))
                ? null
                : getFieldError("firstName", firstName, FIRST_NAME_MESSAGE);
    }

    private FieldError checkLastName(final String lastName) {
        return (lastName == null) || (isLastNameValid(lastName))
                ? null
                : getFieldError("lastName", lastName, LAST_NAME_MESSAGE);
    }

    private FieldError checkEmail(final String email) {
        return (email == null) || (isEmailValid(email))
                ? null
                : getFieldError("email", email, EMAIL_MESSAGE);
    }

    private FieldError checkPhone(final String phone) {
        return (phone == null) || (isPhoneValid(phone))
                ? null
                : getFieldError("phone", phone, PHONE_MESSAGE);
    }

    private FieldError checkGithub(final String github) {
        return (github == null) || (isGithubValid(github))
                ? null
                : getFieldError("github", github, GITHUB_MESSAGE);
    }

    private FieldError checkBio(final String bio) {
        return (bio == null) || (isBioValid(bio))
                ? null
                : getFieldError("bio", bio, BIO_MESSAGE);
    }

    private FieldError checkProjects(final Set<ProjectDTO> projects) {
        return (projects == null) || (projects.size() <= projectsParticipationLimit)
                ? null
                : getFieldError("projects", "", PROJECT_MAX_PARTICIPATION_MESSAGE + projectsParticipationLimit);
    }

    private FieldError getFieldError(final String fieldName, final String fieldValue, final String message) {
        return new FieldError("String", fieldName, fieldValue, false, null, null, message);
    }

}