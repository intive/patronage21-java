package com.intive.patronative.repository;

import com.intive.patronative.repository.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, BigDecimal> {

    Optional<Role> findByName(String name);
}
