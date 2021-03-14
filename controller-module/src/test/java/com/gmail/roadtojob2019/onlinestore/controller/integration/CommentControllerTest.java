package com.gmail.roadtojob2019.onlinestore.controller.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteCommentByIdTest() throws Exception {
        //given
        final long commentId = 1;
        final long articleId = 1;
        //when
        mockMvc.perform(post("/sale/comments/delete")
                .param("commentId", String.valueOf(commentId))
                .param("articleId", String.valueOf(articleId)))
                //then
                .andExpect(status().isFound());
    }
}
