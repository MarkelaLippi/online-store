package com.gmail.roadtojob2019.onlinestore.controller.integration;

import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.LastMiddleFirstName;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    void getPageOfReviewsSortedByCreationTimeTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 3;
        final String sortingParameter = "creationTime";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortingParameter));
        final Review displayedReview = Review.builder().isDisplayed(true).build();
        final Example<Review> reviewPattern = Example.of(displayedReview);
        final Review review = getReview();
        final List<Review> reviews = List.of(review);
        final PageImpl<Review> pageOfReviews = new PageImpl<>(reviews);
        when(reviewRepository.findAll(reviewPattern, pageRequest)).thenReturn(pageOfReviews);
        //when
        mockMvc.perform(get("/reviews")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(reviewRepository, times(1)).findAll(reviewPattern, pageRequest);
    }

    @Test
    void deleteReviewsByIdsTest() throws Exception {
        //given
        final List<Long> reviewsIds = List.of(2L, 4L);
        doNothing().when(reviewRepository).deleteReviewsByIds(reviewsIds);
        //when
        mockMvc.perform(post("/reviews/delete")
                .param("reviewsIds", "2, 4"))
                //then
                .andExpect(status().isOk());
        verify(reviewRepository, times(1)).deleteReviewsByIds(reviewsIds);
    }

    @Test
    void makeReviewHidden() throws Exception {
        //given
        final Long reviewId = 1L;
        final Optional<Review> reviewDisplayed = Optional.of(getReview());
        when(reviewRepository.findById(reviewId)).thenReturn(reviewDisplayed);
        final Review reviewHidden = getReview();
        reviewHidden.setIsDisplayed(false);
        when(reviewRepository.saveAndFlush(reviewHidden)).thenReturn(reviewHidden);
        //when
        mockMvc.perform(post("/reviews/hidden")
                .param("reviewId", reviewId.toString()))
                //then
                .andExpect(status().isOk());
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).saveAndFlush(reviewHidden);
    }

    private LastMiddleFirstName getLastMiddleFirstName() {
        return LastMiddleFirstName.builder()
                .lastName("Markelov")
                .middleName("Alexandrovich")
                .firstName("Sergey")
                .build();
    }

    private User getUser() {
        return User.builder()
                .id(1L)
                .lastMiddleFirstName(getLastMiddleFirstName())
                .email("MarkelaLippi@gmail.com")
                .role(Role.ADMINISTRATOR)
                .password("password")
                .build();
    }

    private Review getReview() {
        return Review.builder()
                .id(1L)
                .content("I wood like to express my opinion about...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .isDisplayed(true)
                .user(getUser())
                .build();
    }
}
