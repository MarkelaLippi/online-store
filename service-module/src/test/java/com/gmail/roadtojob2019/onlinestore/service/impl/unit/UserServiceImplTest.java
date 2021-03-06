package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.repository.CommentRepository;
import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.LastMiddleFirstName;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.EmailService;
import com.gmail.roadtojob2019.onlinestore.service.RandomPasswordGenerator;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchUserNotFoundException;
import com.gmail.roadtojob2019.onlinestore.service.impl.UserServiceImpl;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RandomPasswordGenerator randomPasswordGenerator;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
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
        final Page<User> usersPage = new PageImpl<>(List.of(user));
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);
        //when
        final UsersPageDto usersPageDto = userService.getPageOfUsersSortedByEmail(pageNumber, pageSize);
        //then
        verify(userRepository, times(1)).findAll(pageRequest);
        assertThat(usersPageDto.getUsers(), hasSize(1));
        assertThat(usersPageDto.getTotalNumberOfPages(), is(1));
        assertThat(usersPageDto.getTotalNumberOfUsers(), is(1));
    }

    @Test
    void deleteUsersByIdsTest() {
        //given
        final int[] usersIntIds = {1, 3, 5};
        final List<Long> usersLongIds = List.of(1L, 3L, 5L);
        doNothing().when(userRepository).deleteUsersByIds(usersLongIds);
        doNothing().when(reviewRepository).deleteReviewsByUsersIds(usersLongIds);
        doNothing().when(articleRepository).deleteArticlesByUsersIds(usersLongIds);
        doNothing().when(commentRepository).deleteCommentsByUsersIds(usersLongIds);
        doNothing().when(commentRepository).deleteCommentsByArticlesIds(any());
        //when
        userService.deleteUsersByIds(usersIntIds);
        //then
        verify(reviewRepository, times(1)).deleteReviewsByUsersIds(usersLongIds);
        verify(articleRepository, times(1)).deleteArticlesByUsersIds(usersLongIds);
        verify(userRepository, times(1)).deleteUsersByIds(usersLongIds);
        verify(commentRepository, times(1)).deleteCommentsByUsersIds(usersLongIds);
        verify(commentRepository, times(1)).deleteCommentsByArticlesIds(any());
    }

    @Test
    void changeUserPasswordAndSendItToEmailTest() throws Exception, OnlineMarketSuchUserNotFoundException {
        //given
        final Long userId = 1L;
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        final String password = "12345678";
        final String encodedPassword = "********";
        when(randomPasswordGenerator.generateRandomPassword()).thenReturn(password);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        user.setPassword(password);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        //when
        final boolean result = userService.changeUserPasswordAndSendItToEmail(userId);
        //then
        assertThat(result, is(true));
        verify(userRepository, times(1)).findById(userId);
        verify(randomPasswordGenerator, times(1)).generateRandomPassword();
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    void changeUserRoleTest() throws OnlineMarketSuchUserNotFoundException {
        //given
        final Long userId = 1L;
        final String userRole = "SECURE";
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        final Role newUserRole = Role.SECURE;
        user.setRole(newUserRole);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        //when
        final boolean result = userService.changeUserRole(userId, userRole);
        //then
        assertThat(result, is(true));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    void addUserTest() {
        //given
        final UserDto newUserDto = getUserDto();
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User newUser = getUser(lastMiddleFirstName);
        when(userRepository.saveAndFlush(any())).thenReturn(newUser);
        //when
        final Long addedUserId = userService.addUser(newUserDto);
        //then
        assertThat(addedUserId, is(1L));
        verify(userRepository, times(1)).saveAndFlush(any());
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
                .password("12345678")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .lastName("Markelov")
                .middleName("Alexandrovich")
                .firstName("Sergey")
                .email("S_markelov@tut.by")
                .password("12345678")
                .role(Role.ADMINISTRATOR)
                .build();
    }
}