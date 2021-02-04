package com.gmail.roadtojob2019.onlinestore.restcontroller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestApiArticleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RestApiArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
        final Long createdArticleId = 10L;
        when(articleService.addArticle(articleDto)).thenReturn(createdArticleId);
        //when
        mockMvc.perform(post("/secure/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto)))
                //then
                .andExpect(status().isCreated());
        verify(articleService, times(1)).addArticle(articleDto);
    }

    @Test
    void deleteArticleByIdTest() throws Exception {
        //given
        final Long articleId = 4L;
        doNothing().when(articleService).deleteArticleById(articleId);
        //when
        mockMvc.perform(delete("/secure/articles/4"))
                //then
                .andExpect(status().isOk());
        verify(articleService, times(1)).deleteArticleById(articleId);
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
