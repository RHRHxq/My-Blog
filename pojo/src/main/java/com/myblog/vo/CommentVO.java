package com.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentVO {
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
