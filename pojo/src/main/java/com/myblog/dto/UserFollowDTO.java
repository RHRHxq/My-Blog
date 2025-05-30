package com.myblog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFollowDTO {
    private Long userId;
    private Long followedUserId;
}
