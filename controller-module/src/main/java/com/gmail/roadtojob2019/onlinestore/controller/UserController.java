package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfUsersSortedByEmail(Model model,
                                       @RequestParam(name = "number", required = false, defaultValue = "0") int pageNumber,
                                       @RequestParam(name = "size", required = false, defaultValue = "3") int pageSize) {
        final UsersPageDto pageOfUsers = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        model.addAttribute("users", pageOfUsers.getUsers());
        model.addAttribute("pages", pageOfUsers.getTotalNumberOfPages());
        return "users";
    }
}
