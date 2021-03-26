package com.gmail.roadtojob2019.onlinestore.repository;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item,Long> {

    void deleteItemById(UUID itemId);

    @Query(value = "DELETE FROM Item i WHERE i.id IN :itemsIds")
    @Modifying
    void deleteItemsByIds(@Param(value = "itemsIds") Collection<UUID> itemsIds);

    Item getItemById(UUID itemId);
}
