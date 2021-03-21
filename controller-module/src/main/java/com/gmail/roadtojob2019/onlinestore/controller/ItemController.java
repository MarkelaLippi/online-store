package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;
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
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfItemsSortedByName(final Model model,
                                             @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                             @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        final ItemsPageDto itemsPageDto = itemService.getPageOfItemsSortedByTitle(pageNumber, pageSize);
        final List<ItemDto> itemsOnPage = itemsPageDto.getItems();
        final int totalNumberOfPages = itemsPageDto.getTotalNumberOfPages();
        model.addAttribute("items", itemsOnPage);
        model.addAttribute("pages", totalNumberOfPages);
        return "items";
    }
}
