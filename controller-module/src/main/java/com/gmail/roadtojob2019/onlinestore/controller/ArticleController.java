package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    @GetMapping({"/customer/articles", "/sale/articles"})
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

    @GetMapping({"/customer/articles/{id}", "/sale/articles/{id}"})
    @ResponseStatus(HttpStatus.OK)
    String getArticleByIdWithCommentsSortedByDateDesc(final Model model, @PathVariable(name = "id") final long articleId) throws OnlineMarketSuchArticleNotFoundException {
        final ArticleDto articleDto = articleService.getArticleById(articleId);
        final List<CommentDto> commentDtos = commentService.getCommentsOnArticleSortedByDateDesc(articleId);
        model.addAttribute("article", articleDto);
        model.addAttribute("comments", commentDtos);
        return "article";
    }

    @PostMapping("/sale/articles/delete")
    @ResponseStatus(HttpStatus.FOUND)
    void deleteArticlesByIds(@RequestParam @NotNull final long[] articlesIds, final HttpServletResponse response) throws IOException {
        articleService.deleteArticlesByIds(articlesIds);
        response.sendRedirect("/sale/articles");
    }

    @GetMapping("sale/articles/form")
    @ResponseStatus(HttpStatus.OK)
    String getNewArticleForm() {
        return "articleform";
    }

    @PostMapping("sale/articles/add")
    @ResponseStatus(HttpStatus.CREATED)
    String addNewArticle(final @Valid @ModelAttribute(name = "article") ArticleDto articleDto, final Model model, final Principal principal) {
        final UserDto userDto = userService.getUserByEmail(principal.getName());
        articleDto.setUser(userDto);
        final Long addedArticleId = articleService.addArticle(articleDto);
        model.addAttribute("addedArticleId", addedArticleId);
        return "success";
    }

    @PostMapping("sale/articles/change")
    @ResponseStatus(HttpStatus.OK)
    String changeArticle(final @Valid @ModelAttribute(name = "article") ArticleDto articleDto, final Model model) {
        final Long changedArticleId = articleService.changeArticle(articleDto);
        model.addAttribute("changedArticleId", changedArticleId);
        return "success";
    }
}
