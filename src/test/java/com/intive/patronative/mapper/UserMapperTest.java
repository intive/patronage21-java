package com.intive.patronative.mapper;

import com.intive.patronative.dto.UserEditDTO;
import com.intive.patronative.dto.UserProfileDTO;
import com.intive.patronative.repository.model.Project;
import com.intive.patronative.repository.model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper(new ProjectMapper(), new GroupMapper());

    @ParameterizedTest
    @MethodSource("mapToEntity_testDataProvider")
    void mapToEntity_shouldReturnEntityUserOrNull(final User expected, final UserEditDTO editDTO, final User user,
                                                  final Set<Project> availableProjects) {
        // when
        final var result = userMapper.mapToEntity(editDTO, user, availableProjects);
        // then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> mapToEntity_testDataProvider() {
        return Stream.of(
                Arguments.of(null, null, null, null),
                Arguments.of(null, emptyUserEditDTO(), null, null),
                Arguments.of(new User(), emptyUserEditDTO(), new User(), null),
                Arguments.of(new User(), emptyUserEditDTO(), new User(), getSet(Stream.of(new Project())))
        );
    }

    @ParameterizedTest
    @MethodSource("mapToUserProfileDTO_shouldNotThrow_data")
    void mapToUserProfileDTO_shouldNotThrow(final User entityUser, final Set<Project> projects) {
        assertDoesNotThrow(() -> userMapper.mapToUserProfileDTO(entityUser, projects));
    }

    private static Stream<Arguments> mapToUserProfileDTO_shouldNotThrow_data() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new User(), null),
                Arguments.of(User.builder().projects(Collections.emptySet()).build(), Collections.singleton(null)),
                Arguments.of(User.builder().projects(Collections.singleton(null)).build(), Collections.emptySet()),
                Arguments.of(User.builder().projects(Collections.singleton(new Project())).build(), Collections.singleton(null)),
                Arguments.of(User.builder().projects(Collections.singleton(new Project())).build(), Collections.singleton(new Project())),
                Arguments.of(User.builder().projects(Collections.singleton(new Project())).build(),
                        Collections.singleton(Project.builder().projectRoles(Collections.singleton(null)).build()))
        );
    }

    @ParameterizedTest
    @MethodSource("mapToUserProfileDTO_testDataProvider")
    void mapToUserProfileDTO_shouldReturnNullOrUserProfileDTO(final UserProfileDTO expectedProfile, final User user) {
        // when
        final var result = userMapper.mapToUserProfileDTO(user, Collections.emptySet());
        // then
        assertEquals(expectedProfile, result);
    }

    private static Stream<Arguments> mapToUserProfileDTO_testDataProvider() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(UserProfileDTO.builder().projects(new HashSet<>()).build(), new User())
        );
    }

    private static UserEditDTO emptyUserEditDTO() {
        return new UserEditDTO(null, null, null, null, null, null, null, null);
    }

    private static Set<Object> getSet(final Stream<Object> stream) {
        return stream.collect(Collectors.toSet());
    }

}