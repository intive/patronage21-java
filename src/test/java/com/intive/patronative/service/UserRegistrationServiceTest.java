package com.intive.patronative.service;

import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.exception.AlreadyExistsException;
import com.intive.patronative.mapper.GroupMapper;
import com.intive.patronative.mapper.ProjectMapper;
import com.intive.patronative.mapper.UserMapper;
import com.intive.patronative.repository.GenderRepository;
import com.intive.patronative.repository.ProfileRepository;
import com.intive.patronative.repository.ProjectRepository;
import com.intive.patronative.repository.RoleRepository;
import com.intive.patronative.repository.StatusRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.User;
import com.intive.patronative.validation.TechnologyGroupsValidator;
import com.intive.patronative.validation.UserSearchValidator;
import com.intive.patronative.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserRegistrationServiceTest {

    @Mock
    private UserSearchValidator userSearchValidator;
    @Mock
    private TechnologyGroupsValidator technologyGroupsValidator;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupService technologyGroupService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private GenderRepository genderRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProfileRepository profileRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        final var projectMapper = new ProjectMapper();
        final var groupMapper = new GroupMapper();
        final var userMapper = new UserMapper(projectMapper, groupMapper);
        userService = new UserService(userSearchValidator, technologyGroupsValidator, userValidator, userMapper, userRepository, projectRepository,
                statusRepository, profileRepository, technologyGroupService, roleRepository, genderRepository);
    }

    @Test
    void create_shouldSave() {
        // given
        when(userRepository.existsByLogin(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(UserProviderTestData.getUserEntity());
        when(technologyGroupService.getTechnologyGroupsEntitiesByList(UserProviderTestData.getListOfGroupsDTO()))
                .thenReturn(UserProviderTestData.getListOfTechnologyGroupsEntities());
        when(genderRepository.findByName(UserGender.FEMALE))
                .thenReturn(Optional.of(UserProviderTestData.getGenderFemaleEntity()));
        when(statusRepository.findByName(UserStatus.ACTIVE))
                .thenReturn(Optional.of(UserProviderTestData.getStatusActiveEntity()));
        when(roleRepository.findByName(UserRole.CANDIDATE))
                .thenReturn(Optional.of(UserProviderTestData.getCandidateRoleEntity()));

        // when
        final var response = userService.saveUser(UserProviderTestData.getUserDTO());

        // then
        assertThat(response.getLogin()).isSameAs(UserProviderTestData.getUserDTO().getLogin());
        verify(userRepository).existsByLogin(any(String.class));
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void create_shouldThrowAlreadyExistsException() {
        // given
        when(userRepository.existsByLogin(any())).thenReturn(true);

        // when / then
        assertThrows(AlreadyExistsException.class, () -> userService.saveUser(UserProviderTestData.getUserDTO()));

        // then
        verify(userRepository).existsByLogin(any(String.class));
        verify(userRepository, times(0)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }
}