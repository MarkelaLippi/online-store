package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchUserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfUsersSortedByEmail(final Model model,
                                       @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                       @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        final UsersPageDto pageOfUsers = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        model.addAttribute("users", pageOfUsers.getUsers());
        model.addAttribute("pages", pageOfUsers.getTotalNumberOfPages());
        model.addAttribute("roles", pageOfUsers.getRoles());
        return "users";
    }

    @PostMapping("/users/delete")
    @ResponseStatus(HttpStatus.FOUND)
    void deleteUsersByIds(@RequestParam @NotNull final int[] usersIds, final HttpServletResponse response) throws IOException {
        userService.deleteUsersByIds(usersIds);
        response.sendRedirect("/admin/users");
    }

    @PostMapping("/users/change/password")
    @ResponseStatus(HttpStatus.OK)
    String changeUserPasswordAndSendItToEmail(@RequestParam @NotNull final Long userId, final Model model) throws OnlineMarketSuchUserNotFoundException {
        final boolean result = userService.changeUserPasswordAndSendItToEmail(userId);
        model.addAttribute("result", result);
        model.addAttribute("userWithChangedPasswordId", userId);
        return "success";
    }

    @PostMapping("/users/change/role")
    @ResponseStatus(HttpStatus.FOUND)
    void changeUserRole(@RequestParam final Long userId, @RequestParam final String userRole, final HttpServletResponse response) throws OnlineMarketSuchUserNotFoundException, IOException {
        userService.changeUserRole(userId, userRole);
        response.sendRedirect("/admin/users");
    }

    @GetMapping("/users/form")
    @ResponseStatus(HttpStatus.OK)
    String getNewUserForm() {
        return "user";
    }

    @PostMapping("/users/add")
    @ResponseStatus(HttpStatus.CREATED)
    String addNewUser(final @Valid @ModelAttribute(name = "user") UserDto userDto, final Model model) {
        final Long addedUserId = userService.addUser(userDto);
        model.addAttribute("addedUserId", addedUserId);
        return "success";
    }
}