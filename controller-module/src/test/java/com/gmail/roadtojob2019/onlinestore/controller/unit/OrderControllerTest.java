package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.OrderController;
import com.gmail.roadtojob2019.onlinestore.service.OrderService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrderDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrderItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrdersPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void getPageOfOrdersSortedByDateTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final ItemDto itemDto = getItemDto();
        final OrderItemDto orderItemDto = getOrderItemDto(itemDto);
        final List<OrderItemDto> orderItemDtos = List.of(orderItemDto);
        final OrderDto orderDto = getOrderDto(orderItemDtos);
        final List<OrderDto> orderDtos = List.of(orderDto);
        final OrdersPageDto ordersPageDto = getOrdersPageDto(orderDtos);
        when(orderService.getPageOfOrdersSortedByDate(pageNumber, pageSize)).thenReturn(ordersPageDto);
        //when
        mockMvc.perform(get("/sale/orders")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(orderService, times(1)).getPageOfOrdersSortedByDate(pageNumber, pageSize);
    }

    private OrdersPageDto getOrdersPageDto(List<OrderDto> orderDtos) {
        return OrdersPageDto.builder()
                .totalNumberOfOrders(1)
                .totalNumberOfPages(1)
                .orderDtos(orderDtos)
                .build();
    }

    private OrderDto getOrderDto(List<OrderItemDto> orderItemDtos) {
        return OrderDto.builder()
                .id(1)
                .creationDate(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .customerId(1)
                .status("NEW")
                .orderItemDtos(orderItemDtos)
                .build();
    }

    private OrderItemDto getOrderItemDto(ItemDto itemDto) {
        return OrderItemDto.builder()
                .itemDto(itemDto)
                .amount(2)
                .measureUnit("PIECE")
                .orderId(1)
                .build();
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
