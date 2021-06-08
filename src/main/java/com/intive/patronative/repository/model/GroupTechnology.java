package com.intive.patronative.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "group_technology", schema = "patronative")
public class GroupTechnology {

    @EmbeddedId
    private GroupTechnologyKey id;

    @ManyToOne
    @MapsId("technologyGroupId")
    @JoinColumn(name = "technology_group_id")
    private TechnologyGroup technologyGroup;

    @ManyToOne
    @MapsId("technologyId")
    @JoinColumn(name = "technology_id")
    private Technology technology;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

}