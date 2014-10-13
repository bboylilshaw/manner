package org.jshaw.manner.service;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;

public class UserServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testSave() throws Exception {
//        userService.save(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>()));
    }

    @Test
    public void testAddGroup() throws Exception {
        User user = userService.save(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>()));
        Group group = Group.of("BMI", new Date(), "jason", new HashSet<>());
        userService.addGroup(user,group);
    }
}