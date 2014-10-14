package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Test
    public void testSave() throws Exception {

        List<User> users = new ArrayList<>();
//        users.add(User.of("aaa","aaa","aaa","a@a.com","aaa", Role.ADMIN, new HashSet<>()));
//        users.add(User.of("bbb","bbb","bbb","b@b.com","bbb", Role.ADMIN, new HashSet<>()));
//        users.add(User.of("ccc","ccc","ccc","c@c.com","ccc", Role.ADMIN, new HashSet<>()));
//        users.add(User.of("ddd","ddd","ddd","d@d.com","ddd", Role.ADMIN, new HashSet<>()));
        userRepository.save(users);
//        userRepository.save(User.of("jason", "Yao", "Xiao", "jason@jason.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
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