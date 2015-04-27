package org.jshaw.manner.service;

import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.SignUpForm;
import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.UserRepository;
import org.jshaw.manner.security.UserRepositoryUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
    public void signUp(SignUpForm signUpForm) {
        Assert.notNull(signUpForm);
        User user = new User();
        BeanUtils.copyProperties(signUpForm, user);
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        user.setRole(Role.USER);
        userRepository.save(user);
        //load user and set auth
        logger.info("save user:" + user.toString());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        auth.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    @Transactional
    public User addUser(User user) {
        Assert.notNull(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Assert.notNull(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        Assert.notNull(userId);
        return userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsersInGroup(Long groupId) throws Exception {
        Assert.notNull(groupId);
        Group group = groupRepository.findOne(groupId);
        if(group == null) throw new Exception("group does not exist!");
        return (List<User>) group.getUsers();
    }

}
