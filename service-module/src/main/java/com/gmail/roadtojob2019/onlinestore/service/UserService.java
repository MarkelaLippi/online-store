package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;

import java.util.Set;

public interface UserService {
    Set<UserDto> getUsers();
}
