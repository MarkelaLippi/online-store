package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;

public interface ArticleService {

    ArticlesPageDto getPageOfArticlesSortedByDateDesc(int pageNumber, int pageSize);

    ArticleDto getArticleById(long articleId) throws OnlineMarketSuchArticleNotFoundException;
}
