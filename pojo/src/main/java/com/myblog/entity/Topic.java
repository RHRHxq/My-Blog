package com.myblog.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Topic {
    private Long id;
    private String name;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
    private Long userId;

}
