package com.gmail.roadtojob2019.onlinestore.restcontroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.restcontroller.RestApiItemController;
import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestApiItemController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestApiItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void getItemsTest() throws Exception {
        //given
        final ItemDto itemDto = getItemDto();
        final List<ItemDto> itemsDtos = List.of(itemDto);
        when(itemService.getItems()).thenReturn(itemsDtos);
        //when
        mockMvc.perform(get("/secure/items"))
                //then
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItems();
    }

    @Test
    void getItemByIdTest() throws Exception {
        //given
        final String itemId = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        final ItemDto itemDto = getItemDto();
        when(itemService.getItemById(itemId)).thenReturn(itemDto);
        //when
        mockMvc.perform(get("/secure/items/" + itemId))
                //then
                .andExpect(status().isOk());
        verify(itemService, times(1)).getItemById(itemId);
    }

    @Test
    void addItemTest() throws Exception {
        //given
        final ItemDto itemDto = getItemDto();
        itemDto.setId(null);
        final String addedItemId = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        when(itemService.addItem(itemDto)).thenReturn(addedItemId);
        //when
        mockMvc.perform(post("/secure/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)))
                //then
                .andExpect(status().isCreated());
        verify(itemService, times(1)).addItem(itemDto);
    }

    private ItemDto getItemDto() {
        return ItemDto.builder()
                .id(UUID.fromString("e65a4017-a3d9-4986-8e4a-f2ad9dda077b"))
                .name("Name of item")
                .briefDescription("Brief description of item")
                .currency("USD")
                .amount(new BigDecimal("42.50"))
                .build();
    }
}
