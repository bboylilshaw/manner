package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.domain.Group;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class ItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    GroupRepository groupRepository;

    @Test
    public void testFindByGroup() throws Exception {
        Group group = groupRepository.findOne(1L);
        itemRepository.findByGroup(group, new PageRequest(1, 5, Sort.Direction.ASC, "id"))
                .forEach(e -> {
                    System.out.println(e.getId());
                    System.out.println(e.getContent());
                });
        System.out.println(itemRepository.countByGroup(group));
    }

}