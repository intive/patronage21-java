package com.intive.patronative.repository;

import com.intive.patronative.repository.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GenderRepository extends JpaRepository<Gender, BigDecimal> {
}