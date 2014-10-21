package org.jshaw.manner.repository;

import org.jshaw.manner.domain.Group;
import org.jshaw.manner.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByGroup(Group group);
    List<Item> findByGroup(Group group, Pageable pageable);
    List<Item> findByCreatedBy(Long createdById);
    List<Item> findByOwner(Long ownerId);
}
