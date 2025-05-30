package com.myblog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentReportDTO {
    private Long id;
    private Long commentId;
    private Long userId;
    private String reason;
}
