package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getPageOfUsersSortedByEmailTest() {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final String SORTING_PARAMETER = "email";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(SORTING_PARAMETER));
        final User user = getUser();
        Page<User> usersPage = new PageImpl<User>(List.of(user));
        final UserDto userDto = getUserDto();
        //when
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);
        when(userMapper.fromUserToDto(user)).thenReturn(userDto);
        //then
        final UsersPageDto usersPageDto = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        verify(userRepository, times(1)).findAll(pageRequest);
        verify(userMapper, times(1)).fromUserToDto(user);
        assertThat(usersPageDto.getUsers(), hasSize(1));
        assertThat(usersPageDto.getTotalNumberOfPages(), is(1));
        assertThat(usersPageDto.getTotalNumberOfUsers(), is(1));
        assertThat(usersPageDto.getUsers().get(0), hasProperty("lastName", equalTo("Markelov")));
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .firstName("Sergey")
                .lastName("Markelov")
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .firstName("Sergey")
                .lastName("Markelov")
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }
}