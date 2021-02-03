package com.gmail.roadtojob2019.onlinestore.restcontroller.unit;

import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.restcontroller.RestApiArticleController;
import com.gmail.roadtojob2019.onlinestore.service.ArticleService;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestApiArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestApiArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;
    @MockBean
    private CommentService commentService;

    @Test
    void getArticlesTest() throws Exception {
        //given
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        final List<ArticleDto> articlesDtos = List.of(articleDto);
        when(articleService.getArticles()).thenReturn(articlesDtos);
        //when
        mockMvc.perform(get("/secure/articles"))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticles();
    }

    @Test
    void getArticleByIdTest() throws Exception {
        //given
        final long articleId = 1L;
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        when(articleService.getArticleById(articleId)).thenReturn(articleDto);
        //when
        mockMvc.perform(get("/secure/articles/1"))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).getArticleById(articleId);
    }

    @Test
    void addArticleTest() throws Exception {
        //given
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        final Long createdArticleId=10L;
        when(articleService.addArticle(articleDto)).thenReturn(createdArticleId);
        //when
        mockMvc.perform(post("/secure/articles"))
                //then
                .andExpect(status().isCreated());
        verify(articleService, times(1)).addArticle(articleDto);
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
}
