package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getPageOfUsersSortedByEmail(int pageNumber, int pageSize);
}
