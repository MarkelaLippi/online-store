package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    String getPageOfReviews(Model model,
                            @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                            @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        reviewService.getPageOfReviews(pageNumber, pageSize);
        return "reviews";
    }
}
