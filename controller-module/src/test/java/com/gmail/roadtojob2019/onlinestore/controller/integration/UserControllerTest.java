package com.gmail.roadtojob2019.onlinestore.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.LastMiddleFirstName;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.EmailService;
import com.gmail.roadtojob2019.onlinestore.service.RandomPasswordGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ReviewRepository reviewRepository;

    @MockBean
    RandomPasswordGenerator randomPasswordGenerator;

    @MockBean
    EmailService emailService;

    @Test
    void getPageOfUsersSortedByEmailTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final Sort sortingParameter = Sort.by("email");
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortingParameter);
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final List<User> users = List.of(user);
        final Page<User> page = new PageImpl<>(users);
        when(userRepository.findAll(pageRequest)).thenReturn(page);
        //when
        mockMvc.perform(get("/users")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(userRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void deleteUsersByIdsTest() throws Exception {
        //given
        final List<Long> usersIds = List.of(1L, 3L, 5L);
        doNothing().when(reviewRepository).deleteReviewsByUsersIds(usersIds);
        doNothing().when(userRepository).deleteUsersByIds(usersIds);
        //when
        mockMvc.perform(post("/users/delete")
                .param("usersIds", "1, 3, 5"))
                //then
                .andExpect(status().isOk());
        verify(reviewRepository, times(1)).deleteReviewsByUsersIds(usersIds);
        verify(userRepository, times(1)).deleteUsersByIds(usersIds);
    }

    @Test
    void changeUserPasswordAndSendItToEmailTest() throws Exception {
        //given
        final Long userId = 1L;
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User user = getUser(lastMiddleFirstName);
        final Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(userId)).thenReturn(optionalUser);
        final String randomPassword = "randomPassword";
        when(randomPasswordGenerator.generateRandomPassword()).thenReturn(randomPassword);
        user.setPassword(randomPassword);
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        final String MAIL_SUBJECT = "Your password was changed";
        doNothing().when(emailService).sendNewUserPasswordToEmail(user.getEmail(), MAIL_SUBJECT, randomPassword);
        //when
        mockMvc.perform(post("/users/change/password")
                .param("userId", userId.toString()))
                //then
                .andExpect(status().isOk());
        verify(userRepository, times(1)).findById(userId);
        verify(randomPasswordGenerator, times(1)).generateRandomPassword();
        verify(userRepository, times(1)).saveAndFlush(user);
        verify(emailService, times(1)).sendNewUserPasswordToEmail(user.getEmail(), MAIL_SUBJECT, randomPassword);
    }

    @Test
    void changeUserRoleTest() throws Exception {
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
        mockMvc.perform(post("/users/change/role")
                .param("userId", userId.toString())
                .param("userRole", userRole))
                //then
                .andExpect(status().isOk());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).saveAndFlush(user);
    }

    @Test
    void addUserTest() throws Exception {
        //given
        final LastMiddleFirstName lastMiddleFirstName = getLastMiddleFirstName();
        final User newUser = getUser(lastMiddleFirstName);
        when(userRepository.saveAndFlush(any())).thenReturn(newUser);
        //when
        mockMvc.perform(post("/users/add")
                .param("lastName", "Statkevich")
                .param("middleName", "Viktorovich")
                .param("firstName", "Nikolay")
                .param("role", "SALE")
                .param("email", "Statkevich@gmail.com")
                .param("password", "12345678"))
                //then
                .andExpect(status().isCreated());
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
                .email("MarkelaLippi@gmail.com")
                .role(Role.ADMINISTRATOR)
                .password("password")
                .build();
    }
}