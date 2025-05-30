package com.myblog.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer status;
}
