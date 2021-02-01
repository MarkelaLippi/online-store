package com.gmail.roadtojob2019.onlinestore.restcontroller;

import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/secure")
public class RestApiArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    List<ArticleDto> getArticles() {
        return articleService.getArticles();
    }
}
