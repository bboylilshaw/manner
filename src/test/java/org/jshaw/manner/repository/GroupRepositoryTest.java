package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GroupRepositoryTest extends AbstractIntegrationTest{

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testSave() throws Exception {
        User user = userRepository.findOne(1L);
//        userRepository.save();
        //Group group = Group.of("BMI", new Date(), user.getUsername(), user);
        //groupRepository.save(group);
    }

    @Test
    public void testUpdate() throws Exception {
//        User user = userRepository.findOne(1L);
////        userRepository.save();
//        List<Group> groups = (List<Group>) user.getGroup();
//
//        Group group = groups.get(0);
//        group.setName("Watson");
//        groupRepository.save(group);
    }

}