package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;

import java.util.Collection;

public interface UserService {
    User save(User user);
    void createGroup(User user, Group group);
    Collection<Group> listGroups(Long userId);
    Collection<User> listUsersInGroup(Long groupId);
}
