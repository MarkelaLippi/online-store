package com.gmail.roadtojob2019.onlinestore.restcontroller;

import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure")
public class RestApiItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    List<ItemDto> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    ItemDto getItemById(@PathVariable final String itemId) {
        return itemService.getItemById(itemId);
    }

    @PostMapping("items")
    @ResponseStatus(HttpStatus.CREATED)
    String addItem(@RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto);
    }
}
