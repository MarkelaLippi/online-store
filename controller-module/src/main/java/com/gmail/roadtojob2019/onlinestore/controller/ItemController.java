package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemsPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
        final ItemsPageDto itemsPageDto = itemService.getPageOfItemsSortedByName(pageNumber, pageSize);
        final List<ItemDto> itemsOnPage = itemsPageDto.getItems();
        final int totalNumberOfPages = itemsPageDto.getTotalNumberOfPages();
        model.addAttribute("items", itemsOnPage);
        model.addAttribute("pages", totalNumberOfPages);
        return "items";
    }

    @GetMapping("/items/delete/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    String deleteItemById(@PathVariable @NotNull final String itemId) {
        itemService.deleteItemById(itemId);
        return "forward:/items";
    }

    @PostMapping("/items/delete")
    @ResponseStatus(HttpStatus.FOUND)
    void deleteItemsByIds(@RequestParam @NotNull final String [] itemsIds, final HttpServletResponse response) throws IOException {
        itemService.deleteItemsByIds(itemsIds);
        response.sendRedirect("/sale/items");
    }

    @GetMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    String getItemById(@PathVariable final String itemId, final Model model) {
        final ItemDto itemDto = itemService.getItemById(itemId);
        model.addAttribute("item", itemDto);
        return "item";
    }
}
