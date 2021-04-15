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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"id", "users"})
@Entity
@Table(name = "technology_group", schema = "patronative")
public class TechnologyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "technology_group_generator")
    @SequenceGenerator(name = "technology_group_generator", sequenceName = "technology_group_seq",
            schema = "patronative", allocationSize = 10)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Column(name = "description", length = 256)
    private String description;

    @ManyToMany(mappedBy = "technologyGroups")
    @ToString.Exclude
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "group_technology", schema = "patronative",
            joinColumns = @JoinColumn(name = "technology_group_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id"))
    private Set<Technology> technologies;
}
