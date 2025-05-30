package com.myblog.dto;

import lombok.Data;

@Data
public class TopicDTO {
    private Long id;
    private String name;
    private String description;
    private Integer status;
    private Long userId;
}
