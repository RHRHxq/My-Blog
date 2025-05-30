package com.myblog.entity;

import lombok.Data;

@Data
public class ResultMessage {
    private boolean system;
    private Long fromUserId;
    private Object message;

}
