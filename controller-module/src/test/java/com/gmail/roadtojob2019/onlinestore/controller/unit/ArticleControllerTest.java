package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.ArticleController;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticlesPageDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
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
    @MockBean
    private CommentService commentService;

    @Test
    void getPageOfArticlesSortedByDateDescTest() throws Exception {
        //given
        final int pageNumber = 0;
        final int pageSize = 10;
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
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

    @Test
    void getArticleByIdTest() throws Exception {
        //given
        final long articleId=1;
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        final List<CommentDto> commentDtos = List.of(getCommentDto());
        when(articleService.getArticleById(articleId)).thenReturn(articleDto);
        when(commentService.getCommentsOnArticleSortedByDateDesc(articleId)).thenReturn(commentDtos);
        //when
        mockMvc.perform(get("/customer/articles/1"))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticleById(articleId);
        verify(commentService, times(1)).getCommentsOnArticleSortedByDateDesc(articleId);
    }

    private CommentDto getCommentDto() {
        return CommentDto.builder()
                .id(1L)
                .content("Content...")
                .creationTime(LocalDateTime.of(2021, 1, 24, 17, 46, 21))
                .user(getUserDto())
                .article(getArticleDto(getUserDto()))
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .lastName("Markelov")
                .middleName("Alexandrovich")
                .firstName("Sergey")
                .email("S_markelov@tut.by")
                .password("12345678")
                .role(Role.ADMINISTRATOR)
                .build();
    }

    private ArticleDto getArticleDto(UserDto userDto) {
        return ArticleDto.builder()
                .id(1)
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .title("Title")
                .summary("Content")
                .user(userDto)
                .build();
    }

    private ArticlesPageDto getArticlesPageDto(List<ArticleDto> articleDtos) {
        return ArticlesPageDto.builder()
                .totalNumberOfArticles(1)
                .totalNumberOfPages(1)
                .articles(articleDtos)
                .build();
    }
}
