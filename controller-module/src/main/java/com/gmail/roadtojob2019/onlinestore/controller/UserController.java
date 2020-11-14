package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    String getUsers(Model model,
                    @RequestParam(required = false, defaultValue = "0") int pageNumber,
                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        final List<UserDto> users = userService.getUsers(pageNumber, pageSize);
        model.addAttribute("users", users);
        return "users";
    }
}
