package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.impl.ReviewServiceImpl;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ReviewMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        final Review review = getReview();
        final List<Review> reviews = List.of(review);
        final PageImpl<Review> pageOfReviews = new PageImpl<>(reviews);
        when(reviewRepository.findAll(pageRequest)).thenReturn(pageOfReviews);
        //when
        final ReviewsPageDto reviewsPageDto = reviewService.getPageOfReviewsSortedByCreationTime(pageNumber, pageSize);
        //then
        assertThat(reviewsPageDto.getReviews(), hasSize(1));
        assertThat(reviewsPageDto.getTotalNumberOfPages(), is(1));
        assertThat(reviewsPageDto.getTotalNumberOfReviews(), is(1L));
        verify(reviewRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void deleteReviewsByIdsTest() {
        //given
        final int[] reviewsIntIds = {2, 4};
        final List<Long> reviewsLongIds = List.of(2L, 4L);
        doNothing().when(reviewRepository).deleteReviewsByIds(reviewsLongIds);
        //when
        reviewService.deleteReviewsByIds(reviewsIntIds);
        //then
        verify(reviewRepository, times(1)).deleteReviewsByIds(reviewsLongIds);
    }

    @Test
    void makeReviewHiddenTest() throws Exception {
        //given
        final Long reviewId = 1L;
        final Optional<Review> reviewDisplayed = Optional.of(getReview());
        when(reviewRepository.findById(reviewId)).thenReturn(reviewDisplayed);
        final Review reviewHidden = getReview();
        reviewHidden.setIsDisplayed(false);
        when(reviewRepository.saveAndFlush(reviewHidden)).thenReturn(reviewHidden);
        //when
        final Long hiddenReviewId = reviewService.makeReviewHidden(reviewId);
        //then
        verify(reviewRepository, times(1)).findById(reviewId);
        verify(reviewRepository, times(1)).saveAndFlush(reviewHidden);
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
