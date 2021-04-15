package com.intive.patronative.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"id", "projects"})
@Entity
@Table(name = "project_role", schema = "patronative")
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_role_generator")
    @SequenceGenerator(name = "project_role_generator", sequenceName = "project_role_seq",
            schema = "patronative", allocationSize = 10)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "projectRoles")
    @ToString.Exclude
    private Set<Project> projects;
}
