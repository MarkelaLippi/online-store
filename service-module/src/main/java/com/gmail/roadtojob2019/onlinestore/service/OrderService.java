package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.OrdersPageDto;

public interface OrderService {
    OrdersPageDto getPageOfOrdersSortedByDate(int pageNumber, int pageSize);
}
