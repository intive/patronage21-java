package com.intive.patronative.validation;

import com.intive.patronative.dto.ProjectDTO;
import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.exception.InvalidArgumentException;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class UserValidator {

    private static final int MAX_LOGIN_LENGTH_IN_DATABASE = 32;
    private static final int MAX_FIRST_NAME_LENGTH_IN_DATABASE = 64;
    private static final int MAX_LAST_NAME_LENGTH_IN_DATABASE = 64;
    private static final int MAX_EMAIL_LENGTH_IN_DATABASE = 64;
    private static final int MAX_PHONE_NUMBER_LENGTH_IN_DATABASE = 16;
    private static final int MAX_GITHUB_URL_LENGTH_IN_DATABASE = 256;
    private static final int MAX_BIO_LENGTH_IN_DATABASE = 512;
    private static final String BASE_GITHUB_LINK = "github.com/";
    private static final String FULL_GITHUB_LINK = "https://www.github.com/";
    private static final Set<String> ALLOWED_IMAGE_TYPES = Set.of("image/png", "image/jpeg", "image/gif");

    private static final Matcher FIRST_NAME_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+").matcher("");
    private static final Matcher LAST_NAME_MATCHER = Pattern.compile("^[a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+([- ][a-zA-ZĄąĆćĘęŁłŃńÓóŚśŹźŻż]+)?$")
            .matcher("");
    private static final Matcher LOGIN_MATCHER = Pattern.compile("^[a-zA-Z0-9]+$").matcher("");
    private static final Matcher EMAIL_USERNAME_MATCHER = Pattern.compile("^[a-zA-Z0-9.]+$").matcher("");
    private static final Matcher EMAIL_BASE_MATCHER = Pattern.compile("^@[a-zA-Z]{1,15}[.][a-z]{1,5}$").matcher("");
    private static final Matcher PHONE_MATCHER = Pattern.compile("^[0-9]+$").matcher("");
    private static final Matcher GITHUB_USERNAME_MATCHER = Pattern.compile("^[a-zA-Z0-9]+([- ][a-zA-Z0-9]+)*$")
            .matcher("");
    private static final Matcher GITHUB_LINK_MATCHER = Pattern.compile("^(https?://)?(www.)?" + BASE_GITHUB_LINK + "$")
            .matcher("");

    @Value("${validators.user.phone-number.length}")
    private int phoneNumberLength;
    @Value("${validators.user.first-name.length.min}")
    private int minFirstNameLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.first-name.length.max}")
    private int maxFirstNameLength;
    @Value("${validators.user.last-name.length.min}")
    private int minLastNameLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.last-name.length.max}")
    private int maxLastNameLength;
    @Value("${validators.user.login.length.min}")
    private int minLoginLength;
    @Getter(AccessLevel.PACKAGE)
    @Value("${validators.user.login.length.max}")
    private int maxLoginLength;
    @Value("${validators.user.email-username.length.min}")
    private int minEmailUsernameLength;
    @Value("${validators.user.email-username.length.max}")
    private int maxEmailUsernameLength;
    @Value("${validators.user.github-username.length.min}")
    private int minGithubUsernameLength;
    @Value("${validators.user.github-username.length.max}")
    private int maxGithubUsernameLength;
    @Value("${validators.user.projects.maximum-participation}")
    private int projectsParticipationLimit;

    public void validateUserData(final UserEditDTO userEditDTO) throws InvalidArgumentException {
        final var fieldErrors = getFieldErrors(userEditDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    public void validateRegistrationData(final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        final var fieldErrors = getRegistrationFieldErrors(userRegistrationRequestDTO);

        if (!fieldErrors.isEmpty()) {
            throw new InvalidArgumentException(fieldErrors);
        }
    }

    public void validateUserImage(final MultipartFile image) {
        final var fieldError = checkImage(image);

        if (!isNull(fieldError)) {
            throw new InvalidArgumentException(List.of(fieldError));
        }
    }

    private List<FieldError> getFieldErrors(final UserEditDTO userEditDTO) {
        return Optional.ofNullable(userEditDTO)
                .map(user -> Stream
                        .of(checkLogin(user.getLogin(), false),
                                checkFirstName(user.getFirstName(), false),
                                checkLastName(user.getLastName(), false),
                                checkEmail(user.getEmail(), false),
                                checkPhone(user.getPhoneNumber(), false),
                                checkGithub(user.getGitHubUrl(), false),
                                checkBio(user.getBio(), false),
                                checkProjects(user.getProjects()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<FieldError> getRegistrationFieldErrors(final UserRegistrationRequestDTO userRegistrationRequestDTO) {
        return Optional.ofNullable(userRegistrationRequestDTO)
                .map(user -> Stream
                        .of(checkLogin(userRegistrationRequestDTO.getLogin(), true),
                                checkFirstName(userRegistrationRequestDTO.getFirstName(), true),
                                checkLastName(userRegistrationRequestDTO.getLastName(), true),
                                checkEmail(userRegistrationRequestDTO.getEmail(), true),
                                checkPhone(userRegistrationRequestDTO.getPhoneNumber(), true),
                                checkGithub(userRegistrationRequestDTO.getGitHubUrl(), true))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private FieldError checkLogin(final String login, final boolean isRequired) {
        final var loginMessage = "Letters and numbers, " + ValidationHelper.getMinMaxCharactersMessage(minLoginLength, maxLoginLength);

        return (isRequired || login != null) && !(ValidationHelper.checkLength(login, minLoginLength, maxLoginLength) && isLoginValid(login))
                ? ValidationHelper.getFieldError("login", login, loginMessage)
                : null;
    }

    public boolean isLoginValid(final String login) {
        return (login != null) && (login.length() <= MAX_LOGIN_LENGTH_IN_DATABASE)
                && LOGIN_MATCHER.reset(login).matches();
    }

    private FieldError checkFirstName(final String firstName, final boolean isRequired) {
        final var firstNameMessage = "Only letters, " +
                ValidationHelper.getMinMaxCharactersMessage(minFirstNameLength, maxFirstNameLength);

        return (isRequired || firstName != null) && !(ValidationHelper.checkLength(firstName, minFirstNameLength, maxFirstNameLength) && isFirstNameValid(firstName))
                ? ValidationHelper.getFieldError("firstName", firstName, firstNameMessage)
                : null;
    }

    public boolean isFirstNameValid(final String firstName) {
        return (firstName != null) && (firstName.length() <= MAX_FIRST_NAME_LENGTH_IN_DATABASE)
                && FIRST_NAME_MATCHER.reset(firstName).matches();
    }

    private FieldError checkLastName(final String lastName, final boolean isRequired) {
        final var lastNameMessage = "Only letters, dash or space allowed for two part surnames, each surname " +
                ValidationHelper.getMinMaxCharactersMessage(minLastNameLength, maxLastNameLength);

        return (isRequired || lastName != null) && !(ValidationHelper.checkLength(lastName, minLastNameLength, maxLastNameLength) && isLastNameValid(lastName))
                ? ValidationHelper.getFieldError("lastName", lastName, lastNameMessage)
                : null;
    }

    public boolean isLastNameValid(final String lastName) {
        return (lastName != null) && (lastName.length() <= MAX_LAST_NAME_LENGTH_IN_DATABASE)
                && LAST_NAME_MATCHER.reset(lastName).matches();
    }

    private FieldError checkEmail(final String email, final boolean isRequired) {
        final var emailMessage = "Example e-mail: example.Mail123@mail.com, username part " +
                ValidationHelper.getMinMaxCharactersMessage(minEmailUsernameLength, maxEmailUsernameLength);

        return (isRequired || email != null) && !isEmailValid(email)
                ? ValidationHelper.getFieldError("email", email, emailMessage)
                : null;
    }

    private boolean isEmailValid(final String email) {
        if (email != null) {
            final var splitEmailByAt = email.split("(?=@)");

            if (splitEmailByAt.length == 2) {
                final var emailUsername = splitEmailByAt[0];
                final var emailBase = splitEmailByAt[1];

                return (email.length() <= MAX_EMAIL_LENGTH_IN_DATABASE)
                        && ValidationHelper.checkLength(emailUsername, minEmailUsernameLength, maxEmailUsernameLength)
                        && EMAIL_USERNAME_MATCHER.reset(emailUsername).matches()
                        && EMAIL_BASE_MATCHER.reset(emailBase).matches();
            }
        }

        return false;
    }

    private FieldError checkPhone(final String phone, final boolean isRequired) {
        final var phoneMessage = phoneNumberLength + " digits required";

        return (isRequired || phone != null) && !isPhoneValid(phone)
                ? ValidationHelper.getFieldError("phone", phone, phoneMessage)
                : null;
    }

    private boolean isPhoneValid(final String phone) {
        return (phone != null) && (phone.length() <= MAX_PHONE_NUMBER_LENGTH_IN_DATABASE)
                && ValidationHelper.checkLength(phone, phoneNumberLength, phoneNumberLength)
                && PHONE_MATCHER.reset(phone).matches();
    }

    private FieldError checkGithub(final String github, final boolean isRequired) {
        final var githubMessage = "Letters, numbers and dashes allowed, username minimum " + minGithubUsernameLength +
                " characters, should start with " + FULL_GITHUB_LINK + ", username cannot start or end with dash, username cannot exceed " +
                maxGithubUsernameLength;

        return (isRequired || github != null) && !isGithubValid(github)
                ? ValidationHelper.getFieldError("github", github, githubMessage)
                : null;
    }

    private boolean isGithubValid(final String github) {
        if (github != null) {
            final var splitGithubByBaseLink = github.split("(?<=" + BASE_GITHUB_LINK + ")");

            if (splitGithubByBaseLink.length == 2) {
                final var githubLink = splitGithubByBaseLink[0];
                final var githubUsername = splitGithubByBaseLink[1];

                return (github.length() <= MAX_GITHUB_URL_LENGTH_IN_DATABASE)
                        && ValidationHelper.checkLength(githubUsername, minGithubUsernameLength, maxGithubUsernameLength)
                        && GITHUB_USERNAME_MATCHER.reset(githubUsername).matches()
                        && GITHUB_LINK_MATCHER.reset(githubLink).matches();
            }
        }

        return false;
    }

    private FieldError checkBio(final String bio, final boolean isRequired) {
        final var bioMessage = "Up to " + MAX_BIO_LENGTH_IN_DATABASE + " characters";

        return (isRequired || bio != null) && !isBioValid(bio)
                ? ValidationHelper.getFieldError("bio", bio, bioMessage)
                : null;
    }

    private boolean isBioValid(final String bio) {
        return (bio != null) && (bio.length() <= MAX_BIO_LENGTH_IN_DATABASE);
    }

    private FieldError checkProjects(final Set<ProjectDTO> projects) {
        final var projectMaxParticipationMessage = "Maximum number of projects in which you can participate is: ";

        return (projects == null) || (projects.size() <= projectsParticipationLimit)
                ? null
                : ValidationHelper.getFieldError("projects", "", projectMaxParticipationMessage + projectsParticipationLimit);
    }

    private FieldError checkImage(final MultipartFile image) {
        final var allowedImageFormatsMessage = "Allowed image formats: " + ALLOWED_IMAGE_TYPES.toString();
        final var imageNotFoundMessage = "No image was sent";

        if (isNull(image)) {
            return ValidationHelper.getFieldError("image", null, imageNotFoundMessage);
        }

        return isImageValid(image)
                ? null
                : ValidationHelper.getFieldError("image", image.getContentType(), allowedImageFormatsMessage);
    }

    public static boolean isImageValid(final MultipartFile image) {
        return nonNull(image) && ALLOWED_IMAGE_TYPES.contains(image.getContentType());
    }

}