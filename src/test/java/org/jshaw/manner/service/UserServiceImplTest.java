package org.jshaw.manner.service;

import org.jshaw.manner.Application;
import org.jshaw.manner.domain.Role;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void testSave() throws Exception {
        userService.save(User.of("1","2","3","123@123.com","123", Role.ADMIN));
    }
}