package com.intive.patronative.repository;

import com.intive.patronative.repository.model.RolesInProject;
import com.intive.patronative.repository.model.RolesInProjectKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface RolesInProjectRepository extends JpaRepository<RolesInProject, RolesInProjectKey> {

    Set<RolesInProject> findAllByUserId(BigDecimal id);

}