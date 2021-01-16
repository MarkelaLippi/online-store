package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    @Transactional
    public ArticlesPageDto getPageOfArticlesSortedByDateDesc(final int pageNumber, final int pageSize) {
        final String SORTING_PARAMETER = "creationTime";
        final Sort.Direction SORTING_DIRECTION = Sort.Direction.DESC;
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, SORTING_DIRECTION, SORTING_PARAMETER);
        final Page<Article> pageOfArticles = articleRepository.findAll(pageRequest);
        final long totalNumberOfArticles = pageOfArticles.getTotalElements();
        final int totalNumberOfPages = pageOfArticles.getTotalPages();
        final List<ArticleDto> articlesOnPage = pageOfArticles.stream()
                .map(articleMapper::fromArticleToDto)
                .collect(Collectors.toList());
        return ArticlesPageDto.
                builder()
                .totalNumberOfArticles(totalNumberOfArticles)
                .totalNumberOfPages(totalNumberOfPages)
                .articles(articlesOnPage)
                .build();
    }

    @Override
    @Transactional
    public ArticleDto getArticleById(final long articleId) throws OnlineMarketSuchArticleNotFoundException {
        final Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new OnlineMarketSuchArticleNotFoundException("Article with id = " + articleId + " was not found"));
        return articleMapper.fromArticleToDto(article);
    }
}
