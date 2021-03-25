package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item,Long> {
    void deleteItemById(UUID itemId);
}
