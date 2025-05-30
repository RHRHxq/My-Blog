package com.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReportVO {
    private Long id;
    private Long commentId;
    private Long userId;
    private String reason;
    private Integer status;
    private LocalDateTime createTime;
}
