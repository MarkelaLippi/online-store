package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;

    @Override
    public ArticlesPageDto getPageOfArticlesSortedByDateDesc(int pageNumber, int pageSize) {

        return ArticlesPageDto.
                builder().
                totalNumberOfPages(2)
                .articles(List.of())
                .build();
    }
}
