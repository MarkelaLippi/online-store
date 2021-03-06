package com.gmail.roadtojob2019.onlinestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime creationTime;
    private UserDto user;
    private ArticleDto article;
}


