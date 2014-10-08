package org.jshaw.manner.repository;

import org.jshaw.manner.Application;
import org.jshaw.manner.domain.Role;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Test
    public void testSave() throws Exception {
        userRepository.save(User.of("bboylilshaw","Yao","Xiao", "bboylilshaw@gmail.com", encoder.encode("123"), Role.ADMIN));
    }

    @Test
    public void testDelete() throws Exception {
        userRepository.delete(1L);
    }

}