package com.gmail.roadtojob2019.onlinestore.service.mapper;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mappings({
            @Mapping(target="currency", source="price.currency"),
            @Mapping(target="amount", source="price.amount"),
    })
    ItemDto fromItemToDto(Item item);
    @Mappings({
            @Mapping(target="price.currency", source="currency"),
            @Mapping(target="price.amount", source="amount"),
    })
    Item fromDtoToItem(ItemDto itemDto);
}
