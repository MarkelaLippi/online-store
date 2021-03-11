package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.repository.CommentRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.exception.OnlineMarketSuchArticleNotFoundException;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
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

    @Override
    @Transactional
    public List<ArticleDto> getArticles() {
        final List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(articleMapper::fromArticleToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long addArticle(ArticleDto articleDto) {
        final Article newArticle = articleMapper.fromDtoToArticle(articleDto);
        return articleRepository.save(newArticle).getId();
    }

    @Override
    @Transactional
    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional
    @Override
    public void deleteArticlesByIds(long[] articlesIds) {
        final List<Long> articlesIdsAsLong = Arrays.stream(articlesIds)
                .boxed()
                .collect(Collectors.toList());
        commentRepository.deleteCommentsByArticlesIds(articlesIdsAsLong);
        articleRepository.deleteArticlesByIds(articlesIdsAsLong);
    }

    @Override
    public Long changeArticle(ArticleDto articleDto) throws OnlineMarketSuchArticleNotFoundException {
        final long articleId = articleDto.getId();
        final Article editableArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new OnlineMarketSuchArticleNotFoundException("Article with id = " + articleId + " was not found"));
        editableArticle.setTitle(articleDto.getTitle());
        editableArticle.setSummary(articleDto.getSummary());
        editableArticle.setContent(articleDto.getContent());
        editableArticle.setCreationTime(LocalDateTime.now());
        final Article modifiedArticle = articleRepository.save(editableArticle);
        return modifiedArticle.getId();
    }
}
