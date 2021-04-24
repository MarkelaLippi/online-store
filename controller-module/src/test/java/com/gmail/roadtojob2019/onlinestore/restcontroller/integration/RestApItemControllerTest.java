package com.gmail.roadtojob2019.onlinestore.restcontroller.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.roadtojob2019.onlinestore.service.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("classpath:application-test.properties")
class RestApItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getItemsTest() throws Exception {
        //given
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/secure/items"))
                //then
                .andExpect(status().isOk())
                .andReturn();
        final String result = mvcResult.getResponse().getContentAsString();
        List<ItemDto> items = objectMapper.readValue(result, new TypeReference<>() {
        });
        final ItemDto itemDto = items.get(0);
        assertThat(items, hasSize(4));
        assertThat(itemDto, hasProperty("id", equalTo(UUID.fromString("123e4567-e89b-12d3-a456-556642440000"))));
        assertThat(itemDto, hasProperty("name", equalTo("Tyres Cordiant Road Runner 205/55R16 94H")));
        assertThat(itemDto, hasProperty("briefDescription", equalTo("Summer, for passenger cars")));
        assertThat(itemDto, hasProperty("currency", equalTo("USD")));
        assertThat(itemDto, hasProperty("amount", equalTo(BigDecimal.valueOf(41.65))));
    }

    @Test
    void getItemByIdTest() throws Exception {
        //given
        final String itemId = "123e4567-e89b-12d3-a456-556642440000";
        //when
        final MvcResult mvcResult = mockMvc.perform(get("/secure/items/" + itemId))
                //then
                .andExpect(status().isOk())
                .andReturn();
        final String actualItemDtoAsString = mvcResult.getResponse().getContentAsString();
        final ItemDto actualItemDto = objectMapper.readValue(actualItemDtoAsString, ItemDto.class);
        final UUID expectedId = UUID.fromString(itemId);
        final String expectedName = "Tyres Cordiant Road Runner 205/55R16 94H";
        final String expectedBriefDescription = "Summer, for passenger cars";
        final String expectedCurrency = "USD";
        final BigDecimal expectedAmount = BigDecimal.valueOf(41.65);
        assertThat(actualItemDto, hasProperty("id", equalTo(expectedId)));
        assertThat(actualItemDto, hasProperty("name", equalTo(expectedName)));
        assertThat(actualItemDto, hasProperty("briefDescription", equalTo(expectedBriefDescription)));
        assertThat(actualItemDto, hasProperty("currency", equalTo(expectedCurrency)));
        assertThat(actualItemDto, hasProperty("amount", equalTo(expectedAmount)));
    }

    @Test
    void addItemTest() throws Exception {
        //given
        final ItemDto itemDto = getItemDto();
        //when
        mockMvc.perform(post("/secure/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemDto)))
                //then
                .andExpect(status().isCreated());
    }

    @Test
    void deleteItemById() throws Exception {
        //given
        final String itemId = "e65a4017-a3d9-4986-8e4a-f2ad9dda077b";
        //when
        mockMvc.perform(delete("/secure/items/" + itemId))
                //then
                .andExpect(status().isOk());
    }

    private ItemDto getItemDto() {
        return ItemDto.builder()
                .name("Name of item")
                .briefDescription("Brief description of item")
                .currency("USD")
                .amount(new BigDecimal("42.50"))
                .build();
    }
}