package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User save(User user) {
        //user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void addGroup(User user, Group group) {
        //user.getGroups().add(group);
        group.getUsers().add(user);
        //userRepository.save(user);
        groupRepository.save(group);
    }
}
