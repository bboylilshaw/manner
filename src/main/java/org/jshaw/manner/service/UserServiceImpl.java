package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
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
}
