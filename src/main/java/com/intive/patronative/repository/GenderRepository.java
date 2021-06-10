package com.intive.patronative.repository;

import com.intive.patronative.dto.registration.UserGender;
import com.intive.patronative.repository.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, BigDecimal> {

    Optional<Gender> findByName(UserGender gender);
}
