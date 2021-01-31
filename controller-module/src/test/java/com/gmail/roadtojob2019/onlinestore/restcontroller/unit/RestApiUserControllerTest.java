package com.gmail.roadtojob2019.onlinestore.restcontroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.restcontroller.RestApiUserController;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestApiUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestApiUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void addUserTest() throws Exception {
        //given
        final UserDto newUser = getNewUserDto();
        final Long userId = 10L;
        when(userService.addUser(newUser)).thenReturn(userId);
        //when
        mockMvc.perform(post("/secure/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                //then
                .andExpect(status().isCreated());
        verify(userService, times(1)).addUser(newUser);
    }

    private UserDto getNewUserDto() {
        return UserDto.builder()
                .lastName("Statkevich")
                .middleName("Viktorovich")
                .firstName("Nikolay")
                .role(Role.SALE)
                .email("Statkevich@gmail.com")
                .password("12345678")
                .build();
    }
}