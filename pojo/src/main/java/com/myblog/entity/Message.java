package com.myblog.entity;

import lombok.Data;

@Data
public class Message {
    private Long toUserId;
    private String message;
}
