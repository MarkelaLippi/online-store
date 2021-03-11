package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.CommentController;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private UserService userService;

    @Test
    void deleteCommentByIdTest() throws Exception {
        //given
        final long commentId = 1;
        final long articleId = 1;
        doNothing().when(commentService).deleteCommentById(commentId);
        //when
        mockMvc.perform(post("/sale/comments/delete")
                .param("commentId", String.valueOf(commentId))
                .param("articleId", String.valueOf(articleId)))
                //then
                .andExpect(status().isFound());
        verify(commentService, times(1)).deleteCommentById(commentId);
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
