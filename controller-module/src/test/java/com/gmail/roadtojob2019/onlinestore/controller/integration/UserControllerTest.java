package com.gmail.roadtojob2019.onlinestore.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPageOfUsersSortedByEmailTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        //when
        mockMvc.perform(get("/admin/users")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void deleteUsersByIdsTest() throws Exception {
        //given
        //when
        mockMvc.perform(post("/admin/users/delete")
                .param("usersIds", "1, 3, 5"))
                //then
                .andExpect(status().isFound());
    }

    @Test
    void changeUserPasswordAndSendItToEmailTest() throws Exception {
        //given
        final long userId = 1L;
        //when
        mockMvc.perform(post("/admin/users/change/password")
                .param("userId", Long.toString(userId)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void changeUserRoleTest() throws Exception {
        //given
        final long userId = 1L;
        final String userRole = "SECURE";
        //when
        mockMvc.perform(post("/admin/users/change/role")
                .param("userId", Long.toString(userId))
                .param("userRole", userRole))
                //then
                .andExpect(status().isFound());
    }

    @Test
    void getNewUserFormTest() throws Exception {
        //given
        //when
        mockMvc.perform(get("/admin/users/form"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void addUserTest() throws Exception {
        //given
        //when
        mockMvc.perform(post("/admin/users/add")
                .param("lastName", "Statkevich")
                .param("middleName", "Viktorovich")
                .param("firstName", "Nikolay")
                .param("role", "SALE")
                .param("email", "Statkevich@gmail.com")
                .param("password", "12345678"))
                //then
                .andExpect(status().isCreated());
    }
}