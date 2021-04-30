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
    private static final int PHONE_NUMBER_LENGTH = 9;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_LOGIN_LENGTH = 2;
    private static final int MAX_LOGIN_LENGTH = 15;
    private static final int MIN_EMAIL_USERNAME_LENGTH = 3;
    private static final int MAX_EMAIL_USERNAME_LENGTH = 30;
    private static final int MIN_GITHUB_USERNAME_LENGTH = 4;
    private static final int MAX_GITHUB_USERNAME_LENGTH = 39;
    private static final int MIN_OTHER_LENGTH = 2;
    private static final int MAX_OTHER_LENGTH = 125;
    private static final String BASE_GITHUB_LINK = "github.com/";
    private static final String FULL_GITHUB_LINK = "https://www.github.com/";

    private static final Pattern FIRST_NAME_PATTERN = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{" + MIN_NAME_LENGTH +
            "," + MAX_NAME_LENGTH + "}$");
    private static final Pattern LAST_NAME_PATTERN = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{" + MIN_NAME_LENGTH +
            "," + MAX_NAME_LENGTH + "}([- ][a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]{" + MIN_NAME_LENGTH + "," + MAX_NAME_LENGTH + "})?$");
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]{" + MIN_LOGIN_LENGTH + "," + MAX_LOGIN_LENGTH + "}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9.]{" + MIN_EMAIL_USERNAME_LENGTH +
            "," + MAX_EMAIL_USERNAME_LENGTH + "}+[@][a-zA-Z]{1,15}[.][a-z]{1,5}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{" + PHONE_NUMBER_LENGTH + "}$");
    private static final Pattern GITHUB_PATTERN = Pattern.compile("^(https?://)?(www.)?" + BASE_GITHUB_LINK +
            "[a-zA-Z0-9](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){" + (MIN_GITHUB_USERNAME_LENGTH - 1) + "," + (MAX_GITHUB_USERNAME_LENGTH - 1) + "}$");
    private static final Pattern OTHER_PATTERN = Pattern.compile(
            "^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż0-9 -]" + "{" + MIN_OTHER_LENGTH + "," + MAX_OTHER_LENGTH + "}$");
    private static final Pattern TECHNOLOGY_GROUP_PATTERN = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż0-9() -]+$");

    private static final String FIRST_NAME_MESSAGE = "Only letters, " + MIN_NAME_LENGTH + " to " + MAX_NAME_LENGTH + " letters";
    private static final String LAST_NAME_MESSAGE = "Only letters, dash or space allowed for two part surnames, each surname " +
            MIN_NAME_LENGTH + " to " + MAX_NAME_LENGTH + " letters";
    private static final String LOGIN_MESSAGE = "Letters and numbers, " + MIN_LOGIN_LENGTH + " to " + MAX_LOGIN_LENGTH + " characters";
    private static final String EMAIL_MESSAGE = "Example e-mail: example.Mail123@mail.com, username part " + MIN_EMAIL_USERNAME_LENGTH +
            " to " + MAX_EMAIL_USERNAME_LENGTH + " characters";
    private static final String PHONE_MESSAGE = PHONE_NUMBER_LENGTH + " digits required";
    private static final String GITHUB_MESSAGE = "Letters, numbers and dashes allowed, username minimum " + MIN_GITHUB_USERNAME_LENGTH +
            " characters, should start with " + FULL_GITHUB_LINK + ", username cannot start or end with dash, username cannot exceed " +
            MAX_GITHUB_USERNAME_LENGTH;
    private static final String BIO_MESSAGE = "Bio up to " + BIO_LENGTH + " characters";
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

    public static boolean isLoginValid(final String login) {
        return (login != null) && LOGIN_PATTERN.matcher(login).matches();
    }

    public static boolean isEmailValid(final String email) {
        return (email != null) && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isPhoneValid(final String phone) {
        return (phone != null) && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isGithubValid(final String github) {
        return (github != null) && (GITHUB_PATTERN.matcher(github).matches());
    }

    public static boolean isBioValid(final String bio) {
        return (bio != null) && (bio.length() <= BIO_LENGTH);
    }

    public static boolean isOtherValid(final String other) {
        return (other != null) && OTHER_PATTERN.matcher(other).matches();
    }

    public static boolean isTechnologyGroupValid(final String technologyGroup) {
        return (technologyGroup != null) && TECHNOLOGY_GROUP_PATTERN.matcher(technologyGroup).matches();
    }

    private List<FieldError> getFieldErrors(final UserEditDTO userEditDTO) {
        if (userEditDTO != null) {
            return Stream.of(checkLogin(userEditDTO.getLogin()),
                    checkFirstName(userEditDTO.getFirstName()),
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

    private FieldError checkLogin(final String login) {
        return (login == null) || (isLoginValid(login))
                ? null
                : getFieldError("login", login, LOGIN_MESSAGE);
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