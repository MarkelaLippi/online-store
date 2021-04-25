package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.OrderService;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrderDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.OrdersPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sale")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfOrdersSortedByDate(final Model model,
                                       @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                       @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize){
        final OrdersPageDto pageOfOrdersSortedByDate = orderService.getPageOfOrdersSortedByDate(pageNumber, pageSize);
        final List<OrderDto> orderDtos = pageOfOrdersSortedByDate.getOrderDtos();
        final int totalNumberOfPages = pageOfOrdersSortedByDate.getTotalNumberOfPages();
        model.addAttribute("orders", orderDtos).addAttribute("pages", totalNumberOfPages);
        return "orders";
    }
}
