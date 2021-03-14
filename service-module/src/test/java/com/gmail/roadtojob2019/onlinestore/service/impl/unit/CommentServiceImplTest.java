package com.gmail.roadtojob2019.onlinestore.service.impl.unit;

import com.gmail.roadtojob2019.onlinestore.repository.CommentRepository;
import com.gmail.roadtojob2019.onlinestore.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void deleteCommentByIdTest() {
        //given
        final Long commentId = 4L;
        doNothing().when(commentRepository).deleteById(commentId);
        //when
        commentService.deleteCommentById(commentId);
        //then
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
