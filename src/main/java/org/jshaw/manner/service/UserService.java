package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User add(User user);
    Group createGroup(User user, Group group);
    Collection<Group> listGroups(Long userId);
    Collection<User> listUsersInGroup(Long groupId);
    List<Item> listGroupItems(Long groupId);
//    List<Item> listMyItems(Long groupId);
    Item createItem(Item item, Group group, User user);
}
