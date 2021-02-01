package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import com.gmail.roadtojob2019.onlinestore.service.impl.ArticleServiceImpl;
import com.gmail.roadtojob2019.onlinestore.service.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleMapper articleMapper;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    void getPageOfArticlesSortedByDateDescTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 3;
        final String sortingParameter = "creationTime";
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, sortingParameter);
        final Article article = getArticle();
        final List<Article> articles = List.of(article);
        final PageImpl<Article> pageOfArticles = new PageImpl<>(articles);
        when(articleRepository.findAll(pageRequest)).thenReturn(pageOfArticles);
        //when
        final ArticlesPageDto articlesPageDto = articleService.getPageOfArticlesSortedByDateDesc(pageNumber, pageSize);
        //then
        assertThat(articlesPageDto.getArticles(), hasSize(1));
        assertThat(articlesPageDto.getTotalNumberOfPages(), is(1));
        assertThat(articlesPageDto.getTotalNumberOfArticles(), is(1L));
        verify(articleRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void getArticleByIdTest() throws Exception {
        //given
        final long articleId = 1;
        final Article article = getArticle();
        final Optional<Article> articleOptional = Optional.of(article);
        when(articleRepository.findById(articleId)).thenReturn(articleOptional);
        final ArticleDto articleDto = getArticleDto();
        when(articleMapper.fromArticleToDto(article)).thenReturn(articleDto);
        //when
        final ArticleDto expectedArticleDto = articleService.getArticleById(articleId);
        //then
        assertThat(expectedArticleDto.getSummary(), is("Summary..."));
        verify(articleRepository, times(1)).findById(articleId);
        verify(articleMapper, times(1)).fromArticleToDto(article);
    }

    @Test
    void getArticlesTest() throws Exception {
        //given
        final Article article = getArticle();
        final List<Article> articles = List.of(article);
        when(articleRepository.findAll()).thenReturn(articles);
        final ArticleDto articleDto = getArticleDto();
        when(articleMapper.fromArticleToDto(article)).thenReturn(articleDto);
        //when
        final List<ArticleDto> actualArticles = articleService.getArticles();
        //then
        assertThat(actualArticles, hasSize(1));
        assertThat(actualArticles.get(0).getTitle(), is("Title"));
        assertThat(actualArticles.get(0).getSummary(), is("Summary..."));
        verify(articleRepository, times(1)).findAll();
        verify(articleMapper, times(1)).fromArticleToDto(article);
    }

    private Article getArticle() {
        return Article.builder()
                .id(1L)
                .title("Title")
                .summary("Summary...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .user(User.builder().id(3L).build())
                .build();
    }

    private ArticleDto getArticleDto() {
        return ArticleDto.builder()
                .id(1L)
                .title("Title")
                .summary("Summary...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .user(UserDto.builder().id(3L).build())
                .build();
    }
}
