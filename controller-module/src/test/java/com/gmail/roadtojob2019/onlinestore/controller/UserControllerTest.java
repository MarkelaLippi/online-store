package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Test
    void testGetUsers() throws Exception {
        //given
        final User user = getUser();
        final List<User> userList = List.of(user);
        //when
        when(userRepository.findAll()).thenReturn(userList);
        //then
        final String contentAsString = mockMvc.perform(get("/users"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private User getUser() {
        return User.builder()
                .lastName("Markelov")
                .firstName("Sergey")
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }
}