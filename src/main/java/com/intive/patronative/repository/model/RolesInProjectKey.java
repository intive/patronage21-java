package com.intive.patronative.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RolesInProjectKey implements Serializable {

    @Column(name = "project_id", nullable = false)
    private BigDecimal projectId;

    @Column(name = "project_role_id", nullable = false)
    private BigDecimal projectRoleId;

    @Column(name = "user_id")
    private BigDecimal userId;

}