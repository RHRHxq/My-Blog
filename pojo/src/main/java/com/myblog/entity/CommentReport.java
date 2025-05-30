package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReport {
    private Long id;
    private Long commentId;
    private Long userId;
    private String reason;
    private Integer status;
    private LocalDateTime createTime;
}
