package com.gmail.roadtojob2019.onlinestore.restcontroller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
class RestApiUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUserTest() throws Exception {
        //given
        final UserDto newUser = getNewUserDto();
        //when
        final MvcResult mvcResult = mockMvc.perform(post("/secure/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
                //then
                .andExpect(status().isCreated())
                .andReturn();
        final String result = mvcResult.getResponse().getContentAsString();
        final Long createdUserId = objectMapper.readValue(result, Long.class);
        assertThat(createdUserId, equalTo(7L));
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