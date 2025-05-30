package com.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendingVO {
    private Long id;
    private String title;
    private Long userId;
    private LocalDateTime submitTime;
    private String content;
}
