package com.gmail.roadtojob2019.onlinestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/users")
    String getUsers(){
        return "users";
    }
}
