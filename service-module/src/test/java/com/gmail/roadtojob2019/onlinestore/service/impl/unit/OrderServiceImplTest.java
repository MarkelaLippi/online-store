package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.OrderRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Order;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrdersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.impl.OrderServiceImpl;
import com.gmail.roadtojob2019.onlinestore.service.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void getPageOfOrdersSortedByDateTest() {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final String SORTING_PARAMETER = "creationDate";
        final Sort.Direction SORTING_DIRECTION = Sort.Direction.DESC;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, SORTING_DIRECTION, SORTING_PARAMETER);
        final Order order = getOrder();
        final List<Order> orders = List.of(order);
        final PageImpl<Order> pageOfOrders = new PageImpl<>(orders);
        when(orderRepository.findAll(pageRequest)).thenReturn(pageOfOrders);
        //when
        final OrdersPageDto ordersPageDto = orderService.getPageOfOrdersSortedByDate(pageNumber, pageSize);
        //then
        assertThat(ordersPageDto.getOrderDtos(), hasSize(1));
        assertThat(ordersPageDto.getTotalNumberOfPages(), is(1));
        assertThat(ordersPageDto.getTotalNumberOfOrders(), is(1L));
        verify(orderRepository, times(1)).findAll(pageRequest);
    }

    private Order getOrder() {
        return null;
    }
}
