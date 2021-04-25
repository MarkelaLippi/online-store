package com.gmail.roadtojob2019.onlinestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderDto {
    private long id;
    private LocalDateTime creationDate;
    private long customerId;
    private String status;
    @Builder.Default
    private List<OrderItemDto> orderItemDtos = new ArrayList<>();
}
