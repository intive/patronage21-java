package com.intive.patronative.repository.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "profile", schema = "patronative")
public class Profile {
    @Id
    @EqualsAndHashCode.Exclude
    @Column(name = "user_id", updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal user_id;

    @Column(name = "bio", length = 512)
    private String bio;

    @Column(name = "image")
    private byte[] image;
}
