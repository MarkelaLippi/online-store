package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.ArticleController;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @MockBean
    private UserService userService;

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
    void getArticleByIdWithCommentsSortedByDateDescTest() throws Exception {
        //given
        final long articleId = 1;
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        final List<CommentDto> commentDtos = List.of(getCommentDto());
        when(articleService.getArticleById(articleId)).thenReturn(articleDto);
        when(commentService.getCommentsOnArticleSortedByDateDesc(articleId)).thenReturn(commentDtos);
        //when
        mockMvc.perform(get("/customer/articles/" + articleId))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticleById(articleId);
        verify(commentService, times(1)).getCommentsOnArticleSortedByDateDesc(articleId);
    }

    @Test
    void deleteArticlesByIdsTest() throws Exception {
        //given
        final long[] articlesIds = {1, 3};
        doNothing().when(articleService).deleteArticlesByIds(articlesIds);
        //when
        mockMvc.perform(post("/sale/articles/delete")
                .param("articlesIds", "1 ,3"))
                //then
                .andExpect(status().isFound());
        verify(articleService, times(1)).deleteArticlesByIds(articlesIds);
    }

    @Test
    void addArticleTest() throws Exception {
        //given
        final ArticleDto articleDto = getArticleDto(null);
        final Long articleId = 10L;
        when(articleService.addArticle(articleDto)).thenReturn(articleId);
        //when
        mockMvc.perform(post("/sale/articles/add")
                .param("creationTime", articleDto.getCreationTime().toString())
                .param("title", articleDto.getTitle())
                .param("summary", articleDto.getSummary())
                .param("content", articleDto.getContent()))
                //then
                .andExpect(status().isCreated());
        verify(articleService, times(1)).addArticle(articleDto);
    }

    @Test
    void changeArticleTest() throws Exception {
        //given
        final ArticleDto articleDto = getArticleDto(null);
        articleDto.setId(1L);
        final Long articleId = 1L;
        when(articleService.changeArticle(articleDto)).thenReturn(articleId);
        //when
        mockMvc.perform(post("/sale/articles/change")
                .param("id", String.valueOf(articleDto.getId()))
                .param("creationTime", articleDto.getCreationTime().toString())
                .param("title", articleDto.getTitle())
                .param("summary", articleDto.getSummary())
                .param("content", articleDto.getContent()))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).changeArticle(articleDto);
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
                .creationTime(LocalDateTime.of(2020, 12, 20, 19, 48, 33))
                .title("Title")
                .summary("Summary")
                .content("Content")
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
