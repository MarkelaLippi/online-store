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
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPageOfReviewsSortedByCreationTimeTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 3;
        //when
        mockMvc.perform(get("/admin/reviews")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
    }

    @Test
    void deleteReviewsByIdsTest() throws Exception {
        //given
        //when
        mockMvc.perform(post("/admin/reviews/delete")
                .param("reviewsIds", "2, 4"))
                //then
                .andExpect(status().isFound());
    }

    @Test
    void makeReviewHiddenTest() throws Exception {
        //given
        final long reviewId = 1L;
        //when
        mockMvc.perform(post("/admin/reviews/hidden")
                .param("reviewId", Long.toString(reviewId)))
                //then
                .andExpect(status().isOk());
    }
}
