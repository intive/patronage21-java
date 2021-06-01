package com.intive.patronative.repository;

import com.intive.patronative.repository.model.GroupTechnology;
import com.intive.patronative.repository.model.GroupTechnologyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTechnologyRepository extends JpaRepository<GroupTechnology, GroupTechnologyKey> {
}