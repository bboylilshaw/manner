package org.jshaw.manner.service;

import org.jshaw.manner.domain.Item;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface ItemService {

    Item getItem(Long itemId);

    Collection<Item> getAllItems();

    Item createItem(Long groupId, Item item);

    Item updateItem(Item updatedItem);

    Page<Item> listItemsInGroup(Long groupId, int startPage, int pageSize);

    void deleteItem(Long itemId);

}
