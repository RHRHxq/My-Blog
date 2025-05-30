package com.myblog.dto;


import lombok.Data;

@Data
public class PrivateMessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
}
