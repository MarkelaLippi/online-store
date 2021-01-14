package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.UserController;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UsersPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getPageOfUsersSortedByEmailTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final UserDto userDto = getUserDto();
        final List<UserDto> userDtos = List.of(userDto);
        final UsersPageDto expectedUsersPageDto = getUserPageDto(userDtos);
        when(userService.getPageOfUsersSortedByEmail(pageNumber, pageSize)).thenReturn(expectedUsersPageDto);
        //when
        mockMvc.perform(get("/admin/users")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(userService, times(1)).getPageOfUsersSortedByEmail(pageNumber, pageSize);
    }

    private UsersPageDto getUserPageDto(final List<UserDto> userDtos) {
        return UsersPageDto.builder()
                .totalNumberOfUsers(1)
                .totalNumberOfPages(1)
                .users(userDtos)
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

    @Test
    void deleteUsersByIdsTest() throws Exception {
        //given
        final int[] usersIds = {1, 3, 5};
        doNothing().when(userService).deleteUsersByIds(usersIds);
        //when
        mockMvc.perform(post("/admin/users/delete")
                .param("usersIds", "1, 3, 5"))
                //then
                .andExpect(status().isFound());
        verify(userService, times(1)).deleteUsersByIds(usersIds);
    }

    @Test
    void changeUserPasswordAndSendItToEmailTest() throws Exception {
        //given
        final Long userId = 1L;
        final boolean result = true;
        when(userService.changeUserPasswordAndSendItToEmail(userId)).thenReturn(result);
        //when
        mockMvc.perform(post("/admin/users/change/password")
                .param("userId", userId.toString()))
                //then
                .andExpect(status().isOk());
        verify(userService, times(1)).changeUserPasswordAndSendItToEmail(userId);
    }

    @Test
    void changeUserRoleTest() throws Exception {
        //given
        final Long userId = 1L;
        final String userRole = "SECURE";
        final boolean result = true;
        when(userService.changeUserRole(userId, userRole)).thenReturn(result);
        //when
        mockMvc.perform(post("/admin/users/change/role")
                .param("userId", userId.toString())
                .param("userRole", userRole))
                //then
                .andExpect(status().isFound());
        verify(userService, times(1)).changeUserRole(userId, userRole);
    }

    @Test
    void addUserTest() throws Exception {
        //given
        final UserDto newUser = getNewUserDto();
        final Long userId = 10L;
        when(userService.addUser(newUser)).thenReturn(userId);
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