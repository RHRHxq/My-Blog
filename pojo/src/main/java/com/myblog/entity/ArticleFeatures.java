package com.myblog.entity;

import lombok.Data;

import java.util.List;

@Data
public class ArticleFeatures {
    private Long articleId;
    private Long categoryId;
    private String title;
    public ArticleFeatures(Long articleId, Long categoryId, String title) {
        this.articleId = articleId;
        this.categoryId = categoryId;
        this.title = title;
    }
}
