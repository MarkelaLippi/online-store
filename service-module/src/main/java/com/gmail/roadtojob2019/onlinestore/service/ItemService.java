package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;

public interface ItemService {
    ItemsPageDto getPageOfItemsSortedByTitle(int pageNumber, int pageSize);
}
