package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;

import java.util.List;

public interface ReviewService {

    ReviewsPageDto getPageOfReviewsSortedByCreationTime(int pageNumber, int pageSize);

    void deleteSelectedReviews(int[] reviewsIds);
}
