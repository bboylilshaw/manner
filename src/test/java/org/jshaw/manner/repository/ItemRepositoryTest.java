package org.jshaw.manner.repository;

import org.jshaw.manner.AbstractIntegrationTest;
import org.jshaw.manner.common.Status;
import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.domain.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.Clock;
import java.time.LocalDate;

public class ItemRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

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

    @Test
    public void testSave() throws Exception {
        String content = "test";
        User user = userRepository.findOne(1L);
        //Item item = Item.of(content, user, user, LocalDate.now(Clock.systemUTC()), null, Status.NEW, 0, null, Priority.HIGH, null);
        System.out.println(Status.NEW.getText());
        System.out.println(Status.NEW);
        System.out.println(Status.WORK_IN_PROGRESS);
        LocalDate localDate = LocalDate.now(Clock.systemUTC());
        LocalDate localDate1 = LocalDate.now();
        System.out.println(localDate);
        System.out.println(localDate == localDate1);
    }

}