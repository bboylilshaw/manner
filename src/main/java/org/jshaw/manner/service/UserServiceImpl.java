package org.jshaw.manner.service;

import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.ItemRepository;
import org.jshaw.manner.repository.UserRepository;
import org.jshaw.manner.security.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserRepositoryUserDetailsService userDetailsService;

    @Override
    @Transactional
    public void signup(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setRole(Role.USER);
        userRepository.save(user);
        //load user and set auth
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        auth.setDetails(userDetails);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    @Transactional
    public User add(User user) {
        //user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findOne(userId);
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
    @Transactional(readOnly = true)
    public List<Item> listGroupItems(Long groupId) {
        Group group = groupRepository.findOne(groupId);
        return itemRepository.findByGroup(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> listGroupItems(Long groupId, int startPage, int pageSize) {
        Group group = groupRepository.findOne(groupId);
        PageRequest pageRequest = new PageRequest(startPage, pageSize);
        return itemRepository.findByGroup(group, pageRequest);
    }

    @Override
    @Transactional
    public Item createItem(Long groupId, Item item) {
        Group group = groupRepository.findOne(groupId);
        item.setGroup(group);
        return itemRepository.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> listAllGroups(Long userId) {
        return (List<Group>)userRepository.findOne(userId).getGroups();
    }

    @Override
    @Transactional(readOnly = true)
    public Group getGroupDetails(Long groupId) {
        return groupRepository.findOne(groupId);
    }

}
