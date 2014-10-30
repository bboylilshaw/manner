package org.jshaw.manner.service;

import org.jshaw.manner.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testAdd() throws Exception {
//        userService.add(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>(), new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    public void testAddGroup() throws Exception {
//        User user = userService.add(User.of("jason", "Yao", "Xiao", "jason@jason.com", "123", Role.ADMIN, new HashSet<>(), new ArrayList<>(), new ArrayList<>()));
//        Group group = Group.of("BMI", new Date(), user, new HashSet<>(), new ArrayList<>());
//        userService.createGroup(user, group);
    }

    @Test
    public void testListUsersInGroup() throws Exception {
        testAddGroup();
        userService.listUsersInGroup(1L).forEach(System.out::println);
    }
}