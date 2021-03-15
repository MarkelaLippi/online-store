package com.gmail.roadtojob2019.onlinestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class ItemsPageDto {
    private long totalNumberOfItems;
    private int totalNumberOfPages;
    @Builder.Default
    private List<ItemDto> items = new ArrayList<>();
}
