package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;

public interface ItemService {
    ItemsPageDto getPageOfItemsSortedByName(int pageNumber, int pageSize);

    void deleteItemById(String itemId);

    void deleteItemsByIds(String[] itemsIds);

    ItemDto getItemById(String itemId);
}
