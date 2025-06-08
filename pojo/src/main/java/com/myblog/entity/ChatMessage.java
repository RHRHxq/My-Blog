package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDateTime createTime;
}
