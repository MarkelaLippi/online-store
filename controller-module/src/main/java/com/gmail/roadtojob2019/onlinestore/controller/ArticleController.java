package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    String getPageOfArticlesSortedByDateDesc(final Model model,
                                             @RequestParam(name = "number", required = false, defaultValue = "0") final int pageNumber,
                                             @RequestParam(name = "size", required = false, defaultValue = "3") final int pageSize) {
        final ArticlesPageDto articlesPageDto = articleService.getPageOfArticlesSortedByDateDesc(pageNumber, pageSize);
        final List<ArticleDto> articlesOnPage = articlesPageDto.getArticles();
        final int totalNumberOfPages = articlesPageDto.getTotalNumberOfPages();
        model.addAttribute("articles", articlesOnPage);
        model.addAttribute("pages", totalNumberOfPages);
        return "articles";
    }

    @GetMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.OK)
    String getArticleByIdWithCommentsSortedByDateDesc(final Model model, @PathVariable(name = "id") final long articleId) throws OnlineMarketSuchArticleNotFoundException {
        final ArticleDto articleDto = articleService.getArticleById(articleId);
        final List<CommentDto> commentDtos = commentService.getCommentsOnArticleSortedByDateDesc(articleId);
        model.addAttribute("article", articleDto);
        model.addAttribute("comments", commentDtos);
        return "article";
    }
}
