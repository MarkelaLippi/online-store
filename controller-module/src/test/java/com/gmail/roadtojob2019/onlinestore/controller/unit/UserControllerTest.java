package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.controller.UserController;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getPageOfUsersSortedByEmail() throws Exception {
        //given
        final int pageNumber=0;
        final int pageSize=10;
        final UserDto userDto = getUserDto();
        final List<UserDto> userDtos = List.of(userDto);
        final UsersPageDto expectedUsersPageDto = getUserPageDto(userDtos);
        //when
        when(userService.getPageOfUsersSortedByEmail(pageNumber, pageSize)).thenReturn(expectedUsersPageDto);
        //then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getPageOfUsersSortedByEmail(pageNumber, pageSize);
    }

    private UsersPageDto getUserPageDto(List<UserDto> userDtos) {
        return UsersPageDto.builder()
                .totalNumberOfUsers(1)
                .totalNumberOfPages(1)
                .users(userDtos)
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .lastName("Markelov")
                .firstName("Sergey")
                .email("S_markelov@tut.by")
                .role(Role.ADMINISTRATOR)
                .build();
    }
}