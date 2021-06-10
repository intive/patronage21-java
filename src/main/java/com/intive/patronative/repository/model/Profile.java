package com.intive.patronative.repository.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "profile", schema = "patronative")
public class Profile {
    @Id
    @EqualsAndHashCode.Exclude
    @Column(name = "user_id", updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    private BigDecimal userId;

    @Column(name = "bio", length = 512)
    private String bio;

    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Column(name = "image")
    private byte[] image;
}
