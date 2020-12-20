package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;

public interface ReviewService {
    ReviewsPageDto getPageOfReviews(int pageNumber, int pageSize);
}
