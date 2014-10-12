package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;

public interface UserService {
    User save(User user);
    void addGroup(User user, Group group);
}
