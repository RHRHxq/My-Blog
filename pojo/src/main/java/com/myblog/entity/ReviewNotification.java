package com.myblog.entity;

import lombok.Data;

@Data
public class ReviewNotification {
    private Long articleId;    // 文章ID
    private Long userId;       // 用户ID// 拒绝原因（可选）
    private int status;
}
