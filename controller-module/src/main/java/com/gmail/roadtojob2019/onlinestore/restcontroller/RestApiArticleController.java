package com.gmail.roadtojob2019.onlinestore.restcontroller;

import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.OK)
    ArticleDto getArticleById(@PathVariable(name = "id") long articleId) throws OnlineMarketSuchArticleNotFoundException {
        return articleService.getArticleById(articleId);
    }

    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.CREATED)
    Long addArticle(@RequestBody ArticleDto articleDto) {
        return articleService.addArticle(articleDto);
    }
}
