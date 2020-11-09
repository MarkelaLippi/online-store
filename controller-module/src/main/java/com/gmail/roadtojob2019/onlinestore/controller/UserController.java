package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    String getUsers(){
        return "users";
    }
}
