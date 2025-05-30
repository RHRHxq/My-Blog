package com.myblog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private Long articleId;
    private Long parentId;
    private Long userId;
    private String content;
    private Long replyTo;
}
