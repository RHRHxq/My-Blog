package com.myblog.service;

import com.myblog.dto.ArticleDTO;
import com.myblog.entity.Article;
import com.myblog.entity.Category;
import com.myblog.vo.ArticleVO;
import com.myblog.vo.CategoryVO;
import com.myblog.vo.PendingVO;

import java.util.List;

public interface ArticleService {

    /**
     * 根据筛选条件获取文章列表
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param publishTime 发布时间
     * @param sortField 排序字段
     * @param sortOrder 排序顺序
     * @return 文章列表
     */
    List<ArticleVO> getArticlesByFilterAndSort(Long categoryId, Long tagId, String publishTime, String sortField, String sortOrder);


    /**
     * w
     * @param
     */
    void insertArticle(Article article);

    void deleteArticle(Long id, Long userId);

    void updateArticle(Article article);

    Article selectArticle(Long id);

    List<Article> getDraftArticles();

    void addDraft(Article article);

    /**
     * 将文章移入回收站
     */
    void moveToRecycleBin(Long id, Long userId);

    /**
     * 从回收站恢复文章
     */
    void restoreFromRecycleBin(Long id, Long userId);

    /**
     * 永久删除文章
     */
    void permanentlyDelete(Long id, Long userId);

    /**
     * 获取回收站中的文章列表
     */
    List<Article> getArticlesInRecycleBin(Long userId);

    /**
     * 清理超过7天的回收站文章
     */
    void cleanExpiredRecycleBinArticles();


    void incrementArticleLikes(Long articleId);

    void decrementArticleLikes(Long articleId);

    Long getArticleLikes(Long articleId);

    void incrementArticleViews(Long articleId);

    Long getArticleViews(Long articleId);

    void updateArticleViews();

    void updateArticleLikes();

    void insertCategory(Category category);

    List<CategoryVO> getSubCategories(Long parentId);

    void addPendingArticle(Article article);

    List<PendingVO> getPendingArticles();

    void reviewArticle(Long id, int i,String reason);

    void removePendingArticle(Long id);

    void batchReviewArticles(List<Long> articleIds, int i);

//    void rejectArticle(Long id,  int i);
}
