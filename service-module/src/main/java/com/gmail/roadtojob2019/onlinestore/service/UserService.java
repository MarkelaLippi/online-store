package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;

public interface UserService {
    UsersPageDto getPageOfUsersSortedByEmail(int pageNumber, int pageSize);

    void deleteUsersByIds(int[] usersIds);

    boolean changeUserPasswordAndSendItToEmail(Long userId);
}
