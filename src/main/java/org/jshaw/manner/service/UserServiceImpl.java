package org.jshaw.manner.service;

import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.UserRepository;
import org.jshaw.manner.security.UserRepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepositoryUserDetailsService userDetailsService;

    @Override
    @Transactional
    public void signUp(User user) {
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
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsersInGroup(Long groupId) {
        return (List<User>) groupRepository.findOne(groupId).getUsers();
    }

}
