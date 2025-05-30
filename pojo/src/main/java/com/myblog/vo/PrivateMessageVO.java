package com.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateMessageVO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createTime;
}
