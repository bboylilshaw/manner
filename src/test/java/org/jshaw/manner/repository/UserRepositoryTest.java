package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Role;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    public void testSave() throws Exception {
        userRepository.save(User.of("bboylilshaw","Yao","Xiao", "bboylilshaw@gmail.com", encoder.encode("123"), Role.ADMIN, new HashSet<>()));
    }

    @Test
    public void testAddGroup() throws Exception {
//        User user = userRepository.findOne(1L);
//        Group group = Group.of("BMI", new Date(), user.getId().toString(), null);
//        ArrayList<Group> groupList = new ArrayList<>();
//        groupList.add(group);
//        user.setGroup(groupList);
//        user.setFirstName("Test");
//        userRepository.save(user);
    }

    @Test
    public void testDelete() throws Exception {
        userRepository.delete(1L);
    }

    @Test
    public void testFindByUsername() throws Exception {
    }

}