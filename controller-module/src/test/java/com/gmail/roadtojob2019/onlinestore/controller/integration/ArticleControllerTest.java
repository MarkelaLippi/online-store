package com.gmail.roadtojob2019.onlinestore.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPageOfArticlesSortedByDateDescTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        //when
        mockMvc.perform(get("/customer/articles")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void getArticleByIdTest() throws Exception {
        //given
        final long articleId = 1;
        //when
        mockMvc.perform(get("/customer/articles/" + articleId))
                //then
                .andExpect(status().isOk());
    }
}
