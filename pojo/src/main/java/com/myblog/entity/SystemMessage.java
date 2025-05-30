package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SystemMessage {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private LocalDateTime createTime;
}