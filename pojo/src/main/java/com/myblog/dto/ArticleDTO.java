package com.myblog.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private Long categoryId;
    private Long userId;
    private Integer status;
    private LocalDateTime publishTime;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private LocalDateTime submitTime;


}
