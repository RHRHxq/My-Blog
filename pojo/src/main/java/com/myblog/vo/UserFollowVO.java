package com.myblog.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFollowVO {
    private Long id;
    private Long userId;
    private Long followedUserId;
    private LocalDateTime createTime;
}
