package org.jshaw.manner.security;

import org.jshaw.manner.domain.User;
import org.jshaw.manner.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        if (username.contains("@")) {//user is using email to login
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if(user == null) {
            throw new UsernameNotFoundException("Could not find user!");
        }
        UserRepositoryUserDetails userDetails = new UserRepositoryUserDetails();
        BeanUtils.copyProperties(user, userDetails);
        userDetails.setId(user.getId());
        return userDetails;
    }

}
