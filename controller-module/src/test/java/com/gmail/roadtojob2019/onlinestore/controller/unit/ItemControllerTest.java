package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.ItemController;
import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void getPageOfItemsSortedByTitleTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final ItemDto itemDto = getItemDto();
        final List<ItemDto> itemDtos = List.of(itemDto);
        final ItemsPageDto itemsPageDto = getItemsPageDto(itemDtos);
        when(itemService.getPageOfItemsSortedByTitle(pageNumber, pageSize)).thenReturn(itemsPageDto);
        //when
        mockMvc.perform(get("/sale/items")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(itemService, times(1)).getPageOfItemsSortedByTitle(pageNumber, pageSize);
    }

    private ItemDto getItemDto() {
        return ItemDto.builder()
                .id(UUID.randomUUID())
                .name("Name of item")
                .briefDescription("Brief description of item")
                .currency("USD")
                .price(new BigDecimal("42.50"))
                .build();
    }

    private ItemsPageDto getItemsPageDto(List<ItemDto> itemDtos) {
        return ItemsPageDto.builder()
                .totalNumberOfItems(1)
                .totalNumberOfPages(1)
                .items(itemDtos)
                .build();
    }
}
