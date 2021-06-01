package com.intive.patronative.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Embeddable
@NoArgsConstructor
public class GroupTechnologyKey implements Serializable {

    @Column(name = "technology_group_id", nullable = false)
    private BigDecimal technologyGroupId;

    @Column(name = "technology_id", nullable = false)
    private BigDecimal technologyId;

    @Column(name = "user_id")
    private BigDecimal userId;

}