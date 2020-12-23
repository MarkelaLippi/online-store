package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ReviewMapper;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImpTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewMapper reviewMapper;
    @InjectMocks
    private ReviewServiceImpl reviewService;


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
        final ReviewsPageDto reviewsPageDto = reviewService.getPageOfReviewsSortedByCreationTime(pageNumber, pageSize);
        //then
        assertThat(reviewsPageDto.getReviews(), hasSize(1));
        assertThat(reviewsPageDto.getTotalNumberOfPages(), is(1));
        assertThat(reviewsPageDto.getTotalNumberOfReviews(), is(1L));
        verify(reviewRepository, times(1)).findAll(reviewPattern, pageRequest);
    }

    @Test
    void deleteReviewsByIds(){
        //given
        final int[] reviewsIntIds = {2, 4};
        final List<Long> reviewsLongIds = List.of(2L, 4L);
        doNothing().when(reviewRepository).deleteReviewsByIds(reviewsLongIds);
        //when
        reviewService.deleteReviewsByIds(reviewsIntIds);
        //then
        verify(reviewRepository, times(1)).deleteReviewsByIds(reviewsLongIds);
    }

    private Review getReview() {
        return Review.builder()
                .id(1L)
                .content("I wood like to express my opinion about...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .isDisplayed(true)
                .build();
    }
}
