package com.myblog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SystemMessageVO {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private Integer type;
    private Integer status;
    private Date createTime;
}
