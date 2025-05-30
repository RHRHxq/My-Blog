package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private Long parentId;
    private Long userId;
    private String content;
    private Integer status;
    private Integer likes;
    private LocalDateTime createTime;
    private Long replyTo;
}
