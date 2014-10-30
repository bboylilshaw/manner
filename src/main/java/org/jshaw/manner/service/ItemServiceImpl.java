package org.jshaw.manner.service;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.jshaw.manner.repository.GroupRepository;
import org.jshaw.manner.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Item addItem(Long groupId, Item item) {
        Group group = groupRepository.getOne(groupId);
        item.setGroup(group);
        return itemRepository.save(item);
    }

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    public Item updateItem(Long itemId, Item updatedItem) {
        Item item = itemRepository.getOne(itemId);
        Assert.notNull(item, "item does not exist");
        BeanUtils.copyProperties(updatedItem, item);
        return itemRepository.save(item);
    }

    @Override
    public Page<Item> listItemsInGroup(Long groupId, int startPage, int pageSize) {
        Group group = groupRepository.getOne(groupId);
        Assert.notNull(group);

        PageRequest pageRequest = new PageRequest(startPage, pageSize);
        return itemRepository.findByGroup(group, pageRequest);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.delete(itemId);
    }
}
