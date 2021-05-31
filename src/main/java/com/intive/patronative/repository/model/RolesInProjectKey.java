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
public class RolesInProjectKey implements Serializable {

    @Column(name = "project_id", nullable = false)
    private BigDecimal projectId;

    @Column(name = "project_role_id", nullable = false)
    private BigDecimal projectRoleId;

    @Column(name = "user_id")
    private BigDecimal userId;

}