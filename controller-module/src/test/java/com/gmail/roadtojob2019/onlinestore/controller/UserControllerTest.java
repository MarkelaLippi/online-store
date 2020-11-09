package com.gmail.roadtojob2019.onlinestore.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

/*
    @MockBean
    UserRepository userRepository;
*/

    @Test
    void testGetUsers() throws Exception {
        //given
        //when
        //Mockito.when(userRepository.)
        //then
        final String contentAsString = mockMvc.perform(get("/users"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}