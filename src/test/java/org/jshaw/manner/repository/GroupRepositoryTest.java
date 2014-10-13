package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.domain.Group;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;

public class GroupRepositoryTest extends AbstractIntegrationTest{

    @Autowired
    GroupRepository groupRepository;

    @Test
    public void testSave() throws Exception {
        groupRepository.save(Group.of("BMI", new Date(), "jason", new HashSet<>()));
    }

    @Test
    public void testUpdate() throws Exception {
    }

}