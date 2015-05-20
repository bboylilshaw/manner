package org.jshaw.manner.service;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImplTest extends AbstractIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testAddUser() throws Exception {
        User user = User.of("username", "first", "last", "abc@abc.com", "123", Role.ADMIN, null);
        userService.addUser(user);
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