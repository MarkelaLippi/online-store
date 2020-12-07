package com.gmail.roadtojob2019.onlinestore.controller.integration;

import com.gmail.roadtojob2019.onlinestore.repository.UserRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.LastMiddleFirstName;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
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

    @MockBean
    UserRepository userRepository;

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
        //then
        mockMvc.perform(get("/users")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                .andExpect(status().isOk());
        verify(userRepository, times(1)).findAll(pageRequest);
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

    @Test
    void deleteUsersByIdsTest() throws Exception {
        //given
        final List<Long> usersIds = List.of(1L, 3L, 5L);
        doNothing().when(userRepository).deleteUsersByIds(usersIds);
        //when
        mockMvc.perform(post("/users/delete")
                .param("usersIds", "1, 3, 5"))
                //then
                .andExpect(status().isOk());
        verify(userRepository, times(1)).deleteUsersByIds(usersIds);
    }
}