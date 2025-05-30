package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Article {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private Long categoryId;
    private Long userId;
    private Integer status;
    private Date publishTime;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private Integer isDeleted;
    private Date deleteTime;
    private LocalDateTime submitTime;
}
