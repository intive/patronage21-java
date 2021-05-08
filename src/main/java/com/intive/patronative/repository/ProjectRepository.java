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
    @Query(value = "SELECT DISTINCT ON (p.name) p.* FROM patronative.project p where p.year = :year ORDER BY p.name", nativeQuery = true)
    Set<Project> getDistinctProjectsByYear(@Param("year") int year);

    Set<Project> findAllByYear(final int year);
}