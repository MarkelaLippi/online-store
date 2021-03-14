package com.gmail.roadtojob2019.onlinestore.controller.unit;

import com.gmail.roadtojob2019.onlinestore.controller.CommentController;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

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
}
