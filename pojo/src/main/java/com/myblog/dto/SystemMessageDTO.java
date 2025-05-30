package com.myblog.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SystemMessageDTO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer type;
    private Long status;
}
