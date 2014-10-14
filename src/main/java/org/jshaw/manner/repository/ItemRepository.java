package org.jshaw.manner.repository;

import org.jshaw.manner.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
