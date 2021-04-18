package com.intive.patronative.service;


import com.intive.patronative.dto.profile.UserRole;
import com.intive.patronative.dto.profile.UserStatus;
import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.dto.registration.TechnologyGroupDTO;
import com.intive.patronative.dto.registration.UserRegistrationRequestDTO;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.Role;
import com.intive.patronative.repository.model.Status;
import com.intive.patronative.repository.model.TechnologyGroup;
import com.intive.patronative.repository.model.User;

import java.util.Set;

import static com.intive.patronative.service.ConsentProviderTestData.getFirstConsentDTO;
import static com.intive.patronative.service.ConsentProviderTestData.getFirstConsentEntity;
import static com.intive.patronative.service.ConsentProviderTestData.getSecondConsentDTO;
import static com.intive.patronative.service.ConsentProviderTestData.getSecondConsentEntity;

public class UserProviderTestData {

    public static UserRegistrationRequestDTO getUserDTO() {
        return UserRegistrationRequestDTO.builder()
                .gender(UserGender.FEMALE)
                .login("AnnaNowak")
                .firstName("Anna")
                .lastName("Nowak")
                .email("anna.nowak@gmail.com")
                .phoneNumber("222222222")
                .groups(getListOfGroupsDTO())
                .consents(Set.of(
                        getFirstConsentDTO(true),
                        getSecondConsentDTO(false)
                ))
                .build();
    }

    public static Set<TechnologyGroupDTO> getListOfGroupsDTO() {
        final var groupJava = new TechnologyGroupDTO("Java");
        final var groupJavaScript = new TechnologyGroupDTO("JavaScript");
        final var groupQA = new TechnologyGroupDTO("QA");
        final var groupAndroid = new TechnologyGroupDTO("Mobile (Android)");
        return Set.of(groupJava, groupJavaScript, groupQA, groupAndroid);
    }

    public static User getUserEntity() {
        final var user = new User();
        user.setEmail("anna.nowak@gmail.com");
        user.setLogin("AnnaNowak");
        user.setFirstName("Anna");
        user.setLastName("Nowak");
        user.setPhoneNumber("222222222");
        user.setGender(getGenderFemaleEntity());
        user.setRole(getCandidateRoleEntity());
        user.setStatus(getStatusActiveEntity());
        user.setTechnologyGroups(getListOfTechnologyGroupsEntities());
        user.setConsents(Set.of(
                getFirstConsentEntity(true),
                getSecondConsentEntity(false)
        ));
        return user;
    }

    public static Gender getGenderFemaleEntity() {
        final var gender = new Gender();
        gender.setName("FEMALE");
        return gender;
    }

    public static Status getStatusActiveEntity() {
        final var status = new Status();
        status.setName(UserStatus.ACTIVE.name());
        return status;
    }

    public static Role getCandidateRoleEntity() {
        final var role = new Role();
        role.setName(UserRole.CANDIDATE.name());
        return role;
    }

    public static Set<TechnologyGroup> getListOfTechnologyGroupsEntities() {
        final var groupJava = new TechnologyGroup();
        groupJava.setName("Java");
        final var groupJavaScript = new TechnologyGroup();
        groupJavaScript.setName("JavaScript");
        final var groupQA = new TechnologyGroup();
        groupQA.setName("QA");
        final var groupAndroid = new TechnologyGroup();
        groupAndroid.setName("Mobile (Android)");
        return Set.of(groupJava, groupJavaScript, groupQA, groupAndroid);
    }
}
