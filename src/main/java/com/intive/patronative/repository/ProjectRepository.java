package com.intive.patronative.repository;

import com.intive.patronative.repository.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProjectRepository extends JpaRepository<Project, BigDecimal> {
    @Query("SELECT DISTINCT p.name FROM Project p where p.year = :year")
    Set<String> getDistinctProjectNamesByYear(@Param("year") int year);
}