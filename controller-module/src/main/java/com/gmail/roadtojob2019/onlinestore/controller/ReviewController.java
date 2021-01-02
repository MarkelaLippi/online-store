package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ReviewService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ReviewsPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    @ResponseStatus(HttpStatus.OK)
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

    @PostMapping("/reviews/delete")
    @ResponseStatus(HttpStatus.OK)
    String deleteSelectedReviews(@RequestParam @NotNull final int[] reviewsIds) {
        reviewService.deleteReviewsByIds(reviewsIds);
        return "redirect:/reviews";
    }

    @PostMapping("/reviews/hidden")
    @ResponseStatus(HttpStatus.OK)
    String makeReviewHidden(@RequestParam @NotNull final Long reviewId) throws OnlineMarketSuchReviewNotFoundException {
        final Long hiddenReviewId = reviewService.makeReviewHidden(reviewId);
        return "redirect:/reviews";
    }
}
