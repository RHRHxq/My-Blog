package com.myblog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private String avatarPath;
    private String salt;
}
