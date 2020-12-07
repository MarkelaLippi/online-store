package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.LastMiddleFirstName;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.EmailService;
import com.gmail.roadtojob2019.onlinestore.service.RandomPasswordGenerator;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchUserNotFoundException;
import com.gmail.roadtojob2019.onlinestore.service.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RandomPasswordGenerator randomPasswordGenerator;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getPageOfUsersSortedByEmailTest() {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final String SORTING_PARAMETER = "email";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(SORTING_PARAMETER));
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final Page<User> usersPage = new PageImpl<User>(List.of(user));
        final UserDto userDto = getUserDto();
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);
        when(userMapper.fromUserToDto(user)).thenReturn(userDto);
        //when
        final UsersPageDto usersPageDto = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        //then
        verify(userRepository, times(1)).findAll(pageRequest);
        verify(userMapper, times(1)).fromUserToDto(user);
        assertThat(usersPageDto.getUsers(), hasSize(1));
        assertThat(usersPageDto.getTotalNumberOfPages(), is(1));
        assertThat(usersPageDto.getTotalNumberOfUsers(), is(1));
        assertThat(usersPageDto.getUsers().get(0), hasProperty("lastName", equalTo("Markelov")));
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .lastName("Markelov")
                .middleName("Alexandrovich")
                .firstName("Sergey")
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    @Test
    void deleteUsersByIdsTest() {
        //given
        final int[] usersIntIds={1,3,5};
        final List <Long> usersLongIds=List.of(1L,3L,5L);
        doNothing().when(userRepository).deleteUsersByIds(usersLongIds);
        //when
        userService.deleteUsersByIds(usersIntIds);
        //then
        verify(userRepository,times(1)).deleteUsersByIds(usersLongIds);
    }

    @Test
    void changeUserPasswordAndSendItToEmailTest() throws Exception, OnlineMarketSuchUserNotFoundException {
        //given
        final Long userId = 1L;
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        final String PASSWORD = "password";
        when(randomPasswordGenerator.generateRandomPassword()).thenReturn(PASSWORD);
        user.setPassword(PASSWORD);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        //when
        final boolean result = userService.changeUserPasswordAndSendItToEmail(userId);
        //then
        assertThat(result, is(true));
        verify(userRepository, times(1)).findById(userId);
        verify(randomPasswordGenerator, times(1)).generateRandomPassword();
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    private LastMiddleFirstName getLastMiddleFirstName() {
        return LastMiddleFirstName.builder()
                .lastName("Markelov")
                .middleName("Alexandrovich")
                .firstName("Sergey")
                .build();
    }

    private User getUser(LastMiddleFirstName lastMiddleFirstName) {
        return User.builder()
                .id(1L)
                .lastMiddleFirstName(lastMiddleFirstName)
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }

}