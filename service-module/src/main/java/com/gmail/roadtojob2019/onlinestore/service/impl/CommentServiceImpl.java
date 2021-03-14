package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.repository.CommentRepository;
import com.gmail.roadtojob2019.onlinestore.repository.entity.Comment;
import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import com.gmail.roadtojob2019.onlinestore.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public List<CommentDto> getCommentsOnArticleSortedByDateDesc(long articleId) {
        final List<Comment> comments = commentRepository.getCommentsByArticleIdSortedByDateDesc(articleId);
        return comments.stream()
                .map(commentMapper::fromCommentToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCommentById(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
