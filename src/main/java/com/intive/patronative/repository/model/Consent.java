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
@EqualsAndHashCode(exclude = {"id", "users"})
@Entity
@Table(name = "consent", schema = "patronative")
public class Consent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consent_generator")
    @SequenceGenerator(name = "consent_generator", sequenceName = "consent_seq",
            schema = "patronative", allocationSize = 10)
    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal id;

    @Column(name = "consent_id", nullable = false)
    private BigDecimal consentId;

    @Column(name = "text", length = 256, nullable = false)
    private String text;

    @Column(name = "required")
    private boolean required;

    @ManyToMany(mappedBy = "consents")
    @ToString.Exclude
    private Set<User> users;
}
