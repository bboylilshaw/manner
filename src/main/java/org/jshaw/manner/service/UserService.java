package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void signup(User user);

    User add(User user);

    User getUser(Long userId);

    Group createGroup(User user, Group group);

    Collection<Group> listGroups(Long userId);

    Collection<User> listUsersInGroup(Long groupId);

    List<Item> listGroupItems(Long groupId);

    Page<Item> listGroupItems(Long groupId, int startPage, int pageSize);

    Item createItem(Long groupId, Item item);

    List<Group> listAllGroups(Long userId);

    Group getGroupDetails(Long groupId);

}
