package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfUsersSortedByEmail(Model model,
                                       @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                       @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        final UsersPageDto pageOfUsers = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        model.addAttribute("users", pageOfUsers.getUsers());
        model.addAttribute("pages", pageOfUsers.getTotalNumberOfPages());
        model.addAttribute("roles", pageOfUsers.getRoles());
        return "users";
    }

    @PostMapping("/users/delete")
    @ResponseStatus(HttpStatus.OK)
    String deleteUsersByIds(@RequestParam final int[] usersIds) {
        if (usersIds == null) {
            return "error";
        } else {
            userService.deleteUsersByIds(usersIds);
            return "redirect:/users";
        }
    }

    @PostMapping("/users/change/password")
    @ResponseStatus(HttpStatus.OK)
    String changeUserPasswordAndSendItToEmail(@RequestParam final Long userId) throws OnlineMarketSuchUserNotFoundException {
        if (userId != null) {
            userService.changeUserPasswordAndSendItToEmail(userId);
        }
        return "redirect:/users";
    }

    @PostMapping("/users/change/role")
    @ResponseStatus(HttpStatus.OK)
    String changeUserRole(@RequestParam final Long userId, @RequestParam final String userRole) throws OnlineMarketSuchUserNotFoundException {
        userService.changeUserRole(userId, userRole);
        return "redirect:/users";
    }
}