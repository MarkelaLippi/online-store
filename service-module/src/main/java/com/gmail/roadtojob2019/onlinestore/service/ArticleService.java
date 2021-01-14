package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;

public interface ArticleService {

    ArticlesPageDto getPageOfArticlesSortedByDateDesc(int pageNumber, int pageSize);

}
