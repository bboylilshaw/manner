package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class ItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    GroupRepository groupRepository;

    @Test
    public void testFindByGroup() throws Exception {
        Group group = groupRepository.findOne(1L);
        Page<Item> itemPage = itemRepository.findByGroup(group, new PageRequest(0, 4));
        itemPage.forEach(e -> {
            System.out.println(e.getId());
            System.out.println(e.getContent());
        });
//        System.out.println(itemRepository.countByGroup(group));
        System.out.println(itemPage.isFirst());
        System.out.println(itemPage.hasPrevious());
        System.out.println(itemPage.hasNext());
    }

}