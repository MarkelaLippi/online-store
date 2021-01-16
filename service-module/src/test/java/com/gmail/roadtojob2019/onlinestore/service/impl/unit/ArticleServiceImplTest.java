package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.ArticleRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Article;
import com.gmail.roadtojob2019.onlinestore.repository.entity.User;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
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

    private Article getArticle() {
        return Article.builder()
                .id(1L)
                .title("Title")
                .summary("Summary...")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .user(User.builder().id(3L).build())
                .build();
    }
}
