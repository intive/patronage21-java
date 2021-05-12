package com.intive.patronative;

import com.intive.patronative.repository.RoleRepository;
import com.intive.patronative.repository.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
class PatronativeApplicationTests {

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void asd() {
        final Optional<Role> role = roleRepository.findById(BigDecimal.ONE);
        role.ifPresent(System.out::println);
    }
}
