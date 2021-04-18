package com.intive.patronative.repository;

import com.intive.patronative.repository.model.TechnologyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import java.util.Optional;

@Repository
public interface TechnologyGroupRepository extends JpaRepository<TechnologyGroup, BigDecimal> {

    Optional<TechnologyGroup> findByName(String name);
}
