package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ItemRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Currency;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Item;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Price;
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
import java.util.List;
import java.util.UUID;

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
    void getPageOfItemsSortedByNameTest() throws Exception {
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

    private Item getItem() {
        return Item.builder()
                .id(UUID.randomUUID())
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
}
