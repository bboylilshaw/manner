package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Test
    public void testSave() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.of("jason", "Yao", "Xiao", "jason@jason.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
        users.add(User.of("john", "John", "Doe", "john@john.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
        users.add(User.of("kei", "Kei", "Sang", "kei@kei.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
        users.add(User.of("dino", "Dino", "Huang", "dino@dino.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
        userRepository.save(users);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        userRepository.delete(1L);
    }

    @Test
//    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void testFindOne() throws Exception {
        User user = userRepository.findOne(1L);
        //System.out.println(user);

//        Collection<Group> groups = user.getGroups();
//        groups.forEach(g -> {
//            //System.out.println(g.getName());
//            g.getUsers().forEach(u -> System.out.println(u.getId()));
//        });
    }

}