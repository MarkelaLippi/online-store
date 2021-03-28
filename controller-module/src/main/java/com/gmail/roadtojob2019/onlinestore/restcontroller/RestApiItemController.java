package com.gmail.roadtojob2019.onlinestore.restcontroller;

import com.gmail.roadtojob2019.onlinestore.service.ItemService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
