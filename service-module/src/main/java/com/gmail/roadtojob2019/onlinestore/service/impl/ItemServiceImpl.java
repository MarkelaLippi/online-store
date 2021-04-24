package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ItemRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public ItemsPageDto getPageOfItemsSortedByName(final int pageNumber, final int pageSize) {
        final String SORTING_PARAMETER = "name";
        final Sort.Direction SORTING_DIRECTION = Sort.Direction.ASC;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, SORTING_DIRECTION, SORTING_PARAMETER);
        final Page<Item> pageOfItems = itemRepository.findAll(pageRequest);
        final long totalNumberOfItems = pageOfItems.getTotalElements();
        final int totalNumberOfPages = pageOfItems.getTotalPages();
        final List<ItemDto> itemsOnPage = pageOfItems.stream()
                .map(itemMapper::fromItemToDto)
                .collect(Collectors.toList());
        return ItemsPageDto.
                builder()
                .totalNumberOfItems(totalNumberOfItems)
                .totalNumberOfPages(totalNumberOfPages)
                .items(itemsOnPage)
                .build();
    }

    @Override
    @Transactional
    public void deleteItemById(final String itemId) {
        final UUID itemIdAsUUID = UUID.fromString(itemId);
        itemRepository.deleteItemById(itemIdAsUUID);
    }

    @Override
    @Transactional
    public void deleteItemsByIds(final String[] itemsIdsAsString) {
        final List<UUID> itemsIds = Arrays.stream(itemsIdsAsString)
                .map(UUID::fromString)
                .collect(Collectors.toList());
        itemRepository.deleteItemsByIds(itemsIds);
    }

    @Override
    @Transactional
    public ItemDto getItemById(final String itemId) {
        final Item item = itemRepository.getItemById(UUID.fromString(itemId));
        return itemMapper.fromItemToDto(item);
    }

    @Override
    @Transactional
    public List<ItemDto> getItems() {
        final List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(itemMapper::fromItemToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String addItem(ItemDto itemDto) {
        final Item item = itemMapper.fromDtoToItem(itemDto);
        return itemRepository.save(item).getId().toString();
    }
}
