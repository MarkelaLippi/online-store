package com.gmail.roadtojob2019.onlinestore.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ArticleDto {
    private long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Creation date must not be null")
    private LocalDateTime creationTime;
    @NotBlank(message = "Title must not be null and must contain at least one non-whitespace character")
    private String title;
    @NotBlank(message = "Summary must not be null and must contain at least one non-whitespace character")
    @Size(max = 200, message = "Size of summary must be equal or lower than 200 symbols")
    private String summary;
    @NotBlank(message = "Content must not be null and must contain at least one non-whitespace character")
    @Size(max = 1000, message = "Size of content must be equal or lower than 1000 symbols")
    private String content;
    private UserDto user;
}
