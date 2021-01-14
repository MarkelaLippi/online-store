package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.ArticleController;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    void getPageOfArticlesSortedByDateDescTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final ArticleDto articleDto = getArticleDto();
        final List<ArticleDto> articleDtos = List.of(articleDto);
        final ArticlesPageDto articlesPageDto = getArticlesPageDto(articleDtos);
        when(articleService.getPageOfArticlesSortedByDateDesc(pageNumber, pageSize)).thenReturn(articlesPageDto);
        //when
        mockMvc.perform(get("/customer/articles")
                .param("number", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize)))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).getPageOfArticlesSortedByDateDesc(pageNumber, pageSize);
    }

    private ArticlesPageDto getArticlesPageDto(List<ArticleDto> articleDtos) {
        return ArticlesPageDto.builder()
                .totalNumberOfArticles(5)
                .totalNumberOfPages(1)
                .articles(articleDtos)
                .build();
    }

    private ArticleDto getArticleDto() {
        return ArticleDto.builder()
                .id(1)
                .title("Title")
                .content("Content")
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .build();
    }
}
