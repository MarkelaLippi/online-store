package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.ReviewController;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.ReviewService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @Test
    void getPageOfReviewsSortedByCreationTimeTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 3;
        final ReviewDto reviewDto = getReviewDto();
        final List<ReviewDto> reviewDtos = List.of(reviewDto);
        final ReviewsPageDto reviewsPageDto = getReviewsPageDto(reviewDtos);
        when(reviewService.getPageOfReviewsSortedByCreationTime(pageNumber, pageSize)).thenReturn(reviewsPageDto);
        //when
        mockMvc.perform(get("/admin/reviews")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(reviewService, times(1)).getPageOfReviewsSortedByCreationTime(pageNumber, pageSize);
    }

    @Test
    void deleteReviewsByIdsTest() throws Exception {
        //given
        final int[] reviewsIds = {2, 4};
        doNothing().when(reviewService).deleteReviewsByIds(reviewsIds);
        //when
        mockMvc.perform(post("/admin/reviews/delete")
                .param("reviewsIds", "2, 4"))
                //then
                .andExpect(status().isFound());
        verify(reviewService, times(1)).deleteReviewsByIds(reviewsIds);
    }

    @Test
    void makeReviewHiddenTest() throws Exception {
        //given
        final Long reviewId=1L;
        when(reviewService.makeReviewHidden(reviewId)).thenReturn(reviewId);
        //when
        mockMvc.perform(post("/admin/reviews/hidden")
        .param("reviewId", reviewId.toString()))
                //then
        .andExpect(status().isOk());
        verify(reviewService, times(1)).makeReviewHidden(reviewId);
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

    private ReviewDto getReviewDto() {
        return ReviewDto.builder()
                .id(1L)
                .content("I wood like to express my opinion about...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .isDisplayed(true)
                .user(getUserDto())
                .build();
    }

    private ReviewsPageDto getReviewsPageDto(List<ReviewDto> reviewDtos) {
        return ReviewsPageDto.builder()
                .totalNumberOfReviews(1L)
                .totalNumberOfPages(1)
                .reviews(reviewDtos)
                .build();
    }
}
