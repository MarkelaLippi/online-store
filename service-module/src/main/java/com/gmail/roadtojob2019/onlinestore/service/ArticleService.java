package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;

import java.util.List;

public interface ArticleService {

    ArticlesPageDto getPageOfArticlesSortedByDateDesc(int pageNumber, int pageSize);

    ArticleDto getArticleById(long articleId) throws OnlineMarketSuchArticleNotFoundException;

    List<ArticleDto> getArticles();

    Long addArticle(ArticleDto articleDto);

    void deleteArticleById(Long articleId);

    void deleteArticlesByIds(long[] articlesIds);

    Long changeArticle(ArticleDto articleDto);
}
