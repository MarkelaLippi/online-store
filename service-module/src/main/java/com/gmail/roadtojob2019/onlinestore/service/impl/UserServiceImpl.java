package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UsersPageDto getPageOfUsersSortedByEmail(int pageNumber, int pageSize) {
        final String SORTING_PARAMETER = "email";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(SORTING_PARAMETER));
        final Page<User> pageOfUsers = userRepository.findAll(pageRequest);
        final int totalNumberOfUsers = pageOfUsers.getNumberOfElements();
        final int totalNumberOfPages = pageOfUsers.getTotalPages();
        final List<UserDto> usersOnPage = pageOfUsers.stream()
                .map(userMapper::fromUserToDto)
                .collect(Collectors.toList());
        return UsersPageDto.builder()
                .totalNumberOfUsers(totalNumberOfUsers)
                .totalNumberOfPages(totalNumberOfPages)
                .users(usersOnPage)
                .build();
    }

    @Override
    @Transactional
    public boolean deleteSelectedUsers(int[] usersIds) {
        return false;
    }
}
