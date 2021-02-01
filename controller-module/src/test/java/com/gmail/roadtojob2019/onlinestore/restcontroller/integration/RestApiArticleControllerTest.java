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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        List<ArticleDto> articles = objectMapper.readValue(result, new TypeReference<>() {});
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

    private UserDto getNewUserDto() {
        return UserDto.builder()
                .lastName("Statkevich")
                .middleName("Viktorovich")
                .firstName("Nikolay")
                .role(Role.SALE)
                .email("Statkevich@gmail.com")
                .password("12345678")
                .build();
    }
}