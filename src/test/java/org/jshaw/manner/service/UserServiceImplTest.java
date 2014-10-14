package org.jshaw.manner.service;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class UserServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testAdd() throws Exception {
        userService.add(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>(), new ArrayList<>()));
    }

    @Test
    public void testAddGroup() throws Exception {
        User user = userService.add(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>(), new ArrayList<>()));
        Group group = Group.of("BMI", new Date(), user, new HashSet<>(), new ArrayList<>());
        userService.createGroup(user, group);
    }

    @Test
    public void testListGroups() throws Exception {
        testAddGroup();
        userService.listGroups(1L).forEach(System.out::println);
    }

    @Test
    public void testListUsersInGroup() throws Exception {
        testAddGroup();
        userService.listUsersInGroup(1L).forEach(System.out::println);
    }
}