package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ItemRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Currency;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Price;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.impl.ItemServiceImpl;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ItemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private ItemMapper itemMapper;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void getPageOfItemsSortedByNameTest() {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final String SORTING_PARAMETER = "name";
        final Sort.Direction SORTING_DIRECTION = Sort.Direction.ASC;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, SORTING_DIRECTION, SORTING_PARAMETER);
        final Item item = getItem();
        final List<Item> items = List.of(item);
        final PageImpl<Item> pageOfItems = new PageImpl<>(items);
        when(itemRepository.findAll(pageRequest)).thenReturn(pageOfItems);
        //when
        final ItemsPageDto itemsPageDto = itemService.getPageOfItemsSortedByName(pageNumber, pageSize);
        //then
        assertThat(itemsPageDto.getItems(), hasSize(1));
        assertThat(itemsPageDto.getTotalNumberOfPages(), is(1));
        assertThat(itemsPageDto.getTotalNumberOfItems(), is(1L));
        verify(itemRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void deleteItemsByIdsTest() {
        //given
        final String[] itemsIdsAsString = {"123e4567-e89b-12d3-a456-556642440000", "e65a4017-a3d9-4986-8e4a-f2ad9dda077b"};
        final List<UUID> itemsIds = Arrays.stream(itemsIdsAsString)
                .map(UUID::fromString)
                .collect(Collectors.toList());
        doNothing().when(itemRepository).deleteItemsByIds(itemsIds);
        //when
        itemService.deleteItemsByIds(itemsIdsAsString);
        //then
        verify(itemRepository, times(1)).deleteItemsByIds(itemsIds);
    }

    @Test
    void deleteItemByIdTest() {
        //given
        final String itemIdAsString = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        final UUID itemId = UUID.fromString(itemIdAsString);
        doNothing().when(itemRepository).deleteItemById(itemId);
        //when
        itemService.deleteItemById(itemIdAsString);
        //then
        verify(itemRepository, times(1)).deleteItemById(itemId);
    }

    @Test
    void getItemsTest() {
        //given
        final Item item = getItem();
        final List<Item> items = List.of(item);
        when(itemRepository.findAll()).thenReturn(items);
        final ItemDto itemDto = getItemDto();
        when(itemMapper.fromItemToDto(item)).thenReturn(itemDto);
        //when
        final List<ItemDto> itemDtos = itemService.getItems();
        //then
        final ItemDto actualItemDto = itemDtos.get(0);
        assertThat(actualItemDto.getId(), is(UUID.fromString("e65a4017-a3d9-4986-8e4a-f2ad9dda077b")));
        assertThat(actualItemDto.getName(), is("Name of item"));
        assertThat(actualItemDto.getBriefDescription(), is("Brief description of item"));
        assertThat(actualItemDto.getAmount(), is(new BigDecimal("42.50")));
        assertThat(actualItemDto.getCurrency(), is(Currency.USD.name()));
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void getItemByIdTest() {
        //given
        final String itemIdAsString = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        final UUID itemId = UUID.fromString(itemIdAsString);
        final Item item = getItem();
        when(itemRepository.getItemById(itemId)).thenReturn(item);
        final ItemDto itemDto = getItemDto();
        when(itemMapper.fromItemToDto(item)).thenReturn(itemDto);
        //when
        final ItemDto actualItemDto = itemService.getItemById(itemIdAsString);
        //then
        final UUID expectedItemId = UUID.fromString(itemIdAsString);
        final String expectedName = "Name of item";
        final String expectedBriefDescription = "Brief description of item";
        final BigDecimal expectedAmount = new BigDecimal("42.50");
        final String expectedCurrency = Currency.USD.name();
        assertThat(actualItemDto.getId(), is(expectedItemId));
        assertThat(actualItemDto.getName(), is(expectedName));
        assertThat(actualItemDto.getBriefDescription(), is(expectedBriefDescription));
        assertThat(actualItemDto.getAmount(), is(expectedAmount));
        assertThat(actualItemDto.getCurrency(), is(expectedCurrency));
        verify(itemRepository, times(1)).getItemById(itemId);
    }

    @Test
    void addItemTest() {
        //given
        final Item item = getItem();
        when(itemRepository.save(item)).thenReturn(item);
        final ItemDto itemDto = getItemDto();
        when(itemMapper.fromDtoToItem(itemDto)).thenReturn(item);
        //when
        final String actualItemId = itemService.addItem(itemDto);
        final String expectedItemId = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        assertThat(actualItemId, is(expectedItemId));
        verify(itemRepository, times(1)).save(item);
        verify(itemMapper, times(1)).fromDtoToItem(itemDto);
    }

    private Item getItem() {
        return Item.builder()
                .id(UUID.fromString("e65a4017-a3d9-4986-8e4a-f2ad9dda077b"))
                .name("Name of item")
                .briefDescription("Brief description of item")
                .price(getPrice())
                .build();
    }

    private Price getPrice() {
        return Price.builder()
                .currency(Currency.USD)
                .amount(new BigDecimal("42.50"))
                .build();
    }

    private ItemDto getItemDto() {
        return ItemDto.builder()
                .id(UUID.fromString("e65a4017-a3d9-4986-8e4a-f2ad9dda077b"))
                .name("Name of item")
                .briefDescription("Brief description of item")
                .amount(new BigDecimal("42.50"))
                .currency(Currency.USD.name())
                .build();
    }
}
