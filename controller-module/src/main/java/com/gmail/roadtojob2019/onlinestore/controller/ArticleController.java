package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class ArticleController {

    private ArticleService articleService;

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfArticlesSortedByDateDesc(final Model model,
                                             @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                             @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize){
        articleService.getPageOfArticlesSortedByDateDesc(pageNumber, pageSize);
        return null;
    }
}
