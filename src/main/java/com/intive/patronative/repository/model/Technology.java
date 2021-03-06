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
@EqualsAndHashCode(exclude = {"id", "technologyGroups"})
@Entity
@Table(name = "technology", schema = "patronative")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "technology_generator")
    @SequenceGenerator(name = "technology_generator", sequenceName = "technology_seq",
            schema = "patronative", allocationSize = 10)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal id;

    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "technologies")
    @ToString.Exclude
    private Set<TechnologyGroup> technologyGroups;
}
