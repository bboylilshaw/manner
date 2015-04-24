package org.jshaw.manner.security;

import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Assert.hasText(username);
        User user = userRepository.findByUsernameOrEmailAllIgnoreCase(username, username);
//        if (username.contains("@")) {//user is using email to login
//            user = userRepository.findByEmail(username);
//        } else {
//            user = userRepository.findByUsername(username);
//        }
        if(user == null) {
            throw new UsernameNotFoundException("Could not find user!");
        }
        logger.info("login user is " + user.toString());
        UserRepositoryUserDetails userDetails = new UserRepositoryUserDetails();
        BeanUtils.copyProperties(user, userDetails);
        userDetails.setId(user.getId());
        return userDetails;
    }

}
