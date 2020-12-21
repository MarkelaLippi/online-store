package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ReviewService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    String getPageOfReviewsSortedByCreationTime(Model model,
                                                @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                                @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        final ReviewsPageDto reviewsPageDto = reviewService.getPageOfReviewsSortedByCreationTime(pageNumber, pageSize);
        final List<ReviewDto> reviewsOnPage = reviewsPageDto.getReviews();
        final int totalNumberOfPages = reviewsPageDto.getTotalNumberOfPages();
        model.addAttribute("reviews", reviewsOnPage);
        model.addAttribute("pages", totalNumberOfPages);
        return "reviews";
    }
}
