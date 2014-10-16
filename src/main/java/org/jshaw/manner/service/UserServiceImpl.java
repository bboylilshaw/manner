package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.ItemRepository;
import org.jshaw.manner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public User add(User user) {
        //user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Group createGroup(User user, Group group) {
        //user.getGroups().add(group);
        //userRepository.save(user);
        group.setCreatedBy(user);
        group.getUsers().add(user);
        user.getGroups().add(group);
        return groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Group> listGroups(Long userId) {
        return userRepository.findOne(userId).getGroups();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> listUsersInGroup(Long groupId) {
        return groupRepository.findOne(groupId).getUsers();
    }

    @Override
    public List<Item> listGroupItems(Long groupId) {
        Group group = groupRepository.findOne(groupId);
        return itemRepository.findByGroup(group);
    }

    @Override
    public Item createItem(Long groupId, Item item) {
        Group group = groupRepository.findOne(groupId);
        item.setGroup(group);
        return itemRepository.save(item);
    }
}
