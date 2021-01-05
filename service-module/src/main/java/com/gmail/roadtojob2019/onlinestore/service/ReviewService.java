package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchReviewNotFoundException;

public interface ReviewService {

    ReviewsPageDto getPageOfReviewsSortedByCreationTime(int pageNumber, int pageSize);

    void deleteReviewsByIds(int[] reviewsIds);

    Long makeReviewHidden(Long reviewId) throws OnlineMarketSuchReviewNotFoundException;
}
