package com.intive.patronative.repository;

import com.intive.patronative.model.Group;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMockRepository implements GroupRepository {
    @Override
    public List<Group> getAllGroups() {
        return List.of(
                new Group("Android"),
                new Group("Embedded"),
                new Group("IOS"),
                new Group("Java"),
                new Group("JavaScript"),
                new Group("QA"));
    }
}
