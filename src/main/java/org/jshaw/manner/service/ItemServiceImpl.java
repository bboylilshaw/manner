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

import java.util.Collection;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    public Collection<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item createItem(Long groupId, Item item) {
        Assert.notNull(item);
        //Cannot create item if the item already has id
        if (item.getId() != null) {
            return null;
        }
        Group group = groupRepository.getOne(groupId);
        item.setGroup(group);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Item updatedItem) {
        Assert.notNull(updatedItem, "updated item must not be null");
        //Cannot update item if the item does not have id
        if (updatedItem.getId() == null) {
            return null;
        }
        return itemRepository.save(updatedItem);
    }

    @Override
    public Page<Item> listItemsInGroup(Long groupId, int startPage, int pageSize) {
        Group group = groupRepository.getOne(groupId);
        PageRequest pageRequest = new PageRequest(startPage, pageSize);
        return itemRepository.findByGroup(group, pageRequest);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.delete(itemId);
    }
}
