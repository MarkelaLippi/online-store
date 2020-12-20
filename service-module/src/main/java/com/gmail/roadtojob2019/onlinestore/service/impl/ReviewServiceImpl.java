package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ReviewRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Review;
import com.gmail.roadtojob2019.onlinestore.service.ReviewService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewsPageDto getPageOfReviewsSortedByCreationTime(int pageNumber, int pageSize) {
        final String SORTING_PARAMETER = "creationTime";
        final PageRequest pageRequest = PageRequest.of(pageSize, pageNumber, Sort.by(SORTING_PARAMETER));
        final Page<Review> pageOfReviews = reviewRepository.findAll(pageRequest);
        final long totalNumberOfReviews = pageOfReviews.getTotalElements();
        final int totalNumberOfPages = pageOfReviews.getTotalPages();
        final List<ReviewDto> reviewsOnPage = pageOfReviews.stream()
                .map(reviewMapper::fromReviewToDto)
                .collect(Collectors.toList());
        return ReviewsPageDto.builder()
                .totalNumberOfReviews(totalNumberOfReviews)
                .totalNumberOfPages(totalNumberOfPages)
                .reviews(reviewsOnPage)
                .build();
    }
}
