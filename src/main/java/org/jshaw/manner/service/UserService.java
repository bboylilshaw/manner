package org.jshaw.manner.service;

import org.jshaw.manner.domain.SignUpForm;
import org.jshaw.manner.domain.User;

import java.util.List;

public interface UserService {

    void signUp(SignUpForm signUpForm);

    User addUser(User user);

    User updateUser(User user);

    User getUser(Long userId);

    List<User> listUsersInGroup(Long groupId) throws Exception;

}
