package com.intive.patronative.migration;


import com.intive.patronative.repository.GenderRepository;
import com.intive.patronative.repository.UserRepository;
import com.intive.patronative.repository.model.Gender;
import com.intive.patronative.repository.model.User;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureEmbeddedDatabase
public class DatabaseMigrationTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveGender() throws Exception {
        // given
        final var gender = new Gender();
        gender.setName("OTHER");
        entityManager.persistAndFlush(gender);

        // when
        final var genders = genderRepository.findAll();

        // then
        assertThat(genders).extracting("name").contains("OTHER");
    }

    @Test
    public void shouldSaveUser() {
        // given
        final var newUser = User.builder().login("niko433").userName("Niko").firstName("Nikodem").lastName("Felicjański").email("nikodem-felicjanski@gmail.com").phoneNumber("48565434987").build();
        entityManager.persistAndFlush(newUser);

        // when
        final var expectedUser = userRepository.findById(newUser.getId());

        // then
        assertEquals(expectedUser, Optional.of(newUser));
    }

}