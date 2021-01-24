package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<CommentDto> getCommentsOnArticleSortedByDateDesc(long articleId) {
        return null;
    }
}
