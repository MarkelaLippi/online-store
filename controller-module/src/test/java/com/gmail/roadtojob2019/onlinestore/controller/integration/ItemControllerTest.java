package com.gmail.roadtojob2019.onlinestore.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPageOfItemsSortedByNameTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        //when
        mockMvc.perform(get("/sale/items")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void deleteItemByIdTest() throws Exception {
        //given
        final String itemId = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        //when
        mockMvc.perform(get("/sale/items/delete/" + itemId))
                //then
                .andExpect(status().isOk());
    }
}
