package com.gmail.roadtojob2019.onlinestore.service;

import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsOnArticleSortedByDateDesc(long articleId);

    void deleteCommentById(long commentId);
}

