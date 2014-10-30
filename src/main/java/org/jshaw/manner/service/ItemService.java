package org.jshaw.manner.service;

import org.jshaw.manner.domain.Item;
import org.springframework.data.domain.Page;

public interface ItemService {

    Item addItem(Long groupId, Item item);

    Item getItem(Long itemId);

    Item updateItem(Long itemId, Item updatedItem);

    Page<Item> listItemsInGroup(Long groupId, int startPage, int pageSize);

    void deleteItem(Long itemId);

}
