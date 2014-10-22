package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void signup(User user);
    User add(User user);
    Group createGroup(User user, Group group);
    Collection<Group> listGroups(Long userId);
    Collection<User> listUsersInGroup(Long groupId);
    List<Item> listGroupItems(Long groupId);
    List<Item> listGroupItems(Long groupId, int startPage);
    Item createItem(Long groupId, Item item);
    int getTotalPage(Long groupId);
}
