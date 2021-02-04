package com.gmail.roadtojob2019.onlinestore.restcontroller.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Role;
import com.gmail.roadtojob2019.onlinestore.service.dto.ArticleDto;
import com.gmail.roadtojob2019.onlinestore.service.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
class RestApiArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getArticlesTest() throws Exception {
        //given
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/secure/articles"))
                //then
                .andExpect(status().isOk())
                .andReturn();
        final String result = mvcResult.getResponse().getContentAsString();
        List<ArticleDto> articles = objectMapper.readValue(result, new TypeReference<>() {
        });
        final ArticleDto articleDto = articles.get(0);
        assertThat(articles, hasSize(4));
        assertThat(articleDto, hasProperty("id", equalTo(1L)));
        assertThat(articleDto, hasProperty("title", equalTo("The most popular products")));
        final String expectedSummary = "In this article, the author analyzes" +
                " customer requests for the last 3 months";
        assertThat(articleDto, hasProperty("summary", equalTo(expectedSummary)));
        final String expectedContent = "These products are real legends of the " +
                "modern market. Behind them are amazing, funny and " +
                "often paradoxical stories of creation. " +
                "The best-selling products in the world " +
                "not only bring huge profits to brand " +
                "owners, but also reflect the main " +
                "preferences of consumers...";
        assertThat(articleDto, hasProperty("content", equalTo(expectedContent)));
    }

    @Test
    void getArticleByIdTest() throws Exception {
        //given
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/secure/articles/2"))
                //then
                .andExpect(status().isOk())
                .andReturn();
        final String contentAsString = mvcResult.getResponse().getContentAsString();
        final ArticleDto actualArticle = objectMapper.readValue(contentAsString, ArticleDto.class);
        assertThat(actualArticle, hasProperty("id", equalTo(2L)));
        assertThat(actualArticle, hasProperty("title", equalTo("Choosing car tires")));
        final String expectedSummary = "Expert buyer shares his experience with newcomers";
        assertThat(actualArticle, hasProperty("summary", equalTo(expectedSummary)));
        final String expectedContent = "The choice of tires is an important process, it depends on the handling of the car, dynamics, " +
                "fuel consumption and other parameters. Gradation of automobile rubber is performed according " +
                "to the established criteria and parameters. In the summer a single type of rubber, " +
                "the other in winter. An important criterion is the wear resistance of tires. It is influenced " +
                "by factors-natural (ambient temperature and road surface, precipitation, humidity), driving " +
                "style (aggressive, calm), road quality, intensity of operation, temperature overboard...";
        assertThat(actualArticle, hasProperty("content", equalTo(expectedContent)));
    }

    @Test
    void addArticleTest() throws Exception {
        //given
        final UserDto userDto = getUserDto();
        final ArticleDto articleDto = getArticleDto(userDto);
        //when
        final MvcResult result = mockMvc.perform(post("/secure/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto)))
                //then
                .andExpect(status().isCreated())
                .andReturn();
        final String contentAsString = result.getResponse().getContentAsString();
        final Long actualId = objectMapper.readValue(contentAsString, Long.class);
        assertThat(actualId, equalTo(5L));
    }

    @Test
    void deleteArticleByIdTest() throws Exception {
        //given
        //when
        mockMvc.perform(delete("/secure/articles/3"))
                //then
                .andExpect(status().isOk());
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
}