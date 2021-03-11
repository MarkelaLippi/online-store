package com.gmail.roadtojob2019.onlinestore.controller;

import com.gmail.roadtojob2019.onlinestore.service.CommentService;
import com.gmail.roadtojob2019.onlinestore.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sale")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/delete")
    @ResponseStatus(HttpStatus.FOUND)
    void deleteCommentById(@RequestParam @NotNull final long commentId,
                           @RequestParam @NotNull final long articleId,
                           final HttpServletResponse response) throws IOException {
        commentService.deleteCommentById(commentId);
        response.sendRedirect("/sale/articles/"+articleId);
    }
}
