package com.gmail.roadtojob2019.onlinestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrdersPageDto {
    private long totalNumberOfOrders;
    private int totalNumberOfPages;
    @Builder.Default
    private List<OrderDto> orderDtos = new ArrayList<>();
}
