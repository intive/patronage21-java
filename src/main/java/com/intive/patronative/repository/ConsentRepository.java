package com.intive.patronative.repository;

import com.intive.patronative.repository.model.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, BigDecimal> {
}