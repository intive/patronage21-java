package com.intive.patronative.repository;

import com.intive.patronative.repository.model.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, BigDecimal> {
    @Query("SELECT DISTINCT r.name FROM ProjectRole r INNER JOIN r.projects p where p.id = :id")
    Set<String> getRolesByProject(@Param("id") BigDecimal projectId);
}