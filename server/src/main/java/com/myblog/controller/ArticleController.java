package com.myblog.controller;

import com.myblog.config.RequiredRoles;
import com.myblog.dto.ArticleDTO;
import com.myblog.dto.CategoryDTO;
import com.myblog.entity.Article;
import com.myblog.entity.Category;
import com.myblog.entity.SystemMessage;
import com.myblog.properties.JwtProperties;
import com.myblog.result.Result;
import com.myblog.service.ArticleService;
import com.myblog.utils.AliOssUtil;
import com.myblog.vo.ArticleVO;
import com.myblog.vo.CategoryVO;
import com.myblog.vo.PendingVO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private JwtTokenController jwtTokenController;
//    @Autowired
//    private RabbitTemplate  rabbitTemplate;

    /**
     * 获取文章列表
     */
    @GetMapping("/articles")
    public Result<List<ArticleVO>> getArticles(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long tagId, @RequestParam(required = false) String publishTime, @RequestParam(defaultValue = "views") String sortField, @RequestParam(defaultValue = "desc") String sortOrder) {
        log.info("获取文章列表");
        List<ArticleVO> articles = articleService.getArticlesByFilterAndSort(categoryId, tagId, publishTime, sortField, sortOrder);
        return Result.success(articles);
    }

    /**
     * 文章的增删改查
     * 把待审文章添加到redis
     */
    @RequiredRoles({"user"})
    @PostMapping("/article")
    public Result<String> addArticle(@RequestBody ArticleDTO articleDTO, @RequestHeader("token") String token) {
        log.info("添加文章");
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        Long userId = jwtTokenController.getUserId(token);
        article.setUserId(userId);
        article.setStatus(1);
        article.setSubmitTime(LocalDateTime.now());
        articleService.insertArticle(article);
        articleService.addPendingArticle(article);
        return Result.success("添加成功");
    }

    @RequiredRoles({"user"})
    @DeleteMapping("/article/{id}")
    public Result<String> deleteArticle(@PathVariable Long id, @RequestHeader("token") String token) {
        log.info("删除文章");
        Long userId = jwtTokenController.getUserId(token);
        articleService.deleteArticle(id, userId);
        return Result.success("删除成功");
    }

    @RequiredRoles({"user"})
    @PutMapping("/article/{id}")
    public Result<String> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        log.info("修改文章");
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        article.setId(id);
        articleService.updateArticle(article);
        return Result.success("修改成功");
    }

    /**
     * 获取文章详情
     * 同时文章访问量加一
     */
    @GetMapping("/article/{id}")
    public Result<ArticleVO> getArticle(@PathVariable Long id) {
        log.info("获取文章");
        Article article = articleService.selectArticle(id);
        articleService.incrementArticleViews(id);
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        return Result.success(articleVO);
    }

    /**
     * 添加草稿
     */
    @RequiredRoles({"user"})
    @PostMapping("/draft")
    public Result<String> addDraft(@RequestBody ArticleDTO articleDTO, @RequestHeader("token") String token) {
        log.info("添加草稿");
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);

        Long userId = jwtTokenController.getUserId(token);
        article.setUserId(userId);
        article.setStatus(0);
        articleService.addDraft(article);
        return Result.success("添加成功");
    }

    /**
     * 获取草稿箱文章列表
     */
    @RequiredRoles({"user"})
    @GetMapping("/draft")
    public Result<List<Article>> getDraftArticles() {
        List<Article> draftArticles = articleService.getDraftArticles();
        return Result.success(draftArticles);
    }

    @RequiredRoles({"user"})
    @DeleteMapping("/article/recycle/{id}")
    public Result<String> moveToRecycleBin(@PathVariable Long id, @RequestHeader("token") String token) {
        log.info("将文章移入回收站");
        Long userId = jwtTokenController.getUserId(token);
        articleService.moveToRecycleBin(id, userId);
        return Result.success("文章已移入回收站");
    }

    /**
     * 从回收站恢复文章
     */
    @RequiredRoles({"user"})
    @PutMapping("/article/recycle/restore/{id}")
    public Result<String> restoreFromRecycleBin(@PathVariable Long id, @RequestHeader("token") String token) {
        log.info("从回收站恢复文章");
        Long userId = jwtTokenController.getUserId(token);
        articleService.restoreFromRecycleBin(id, userId);
        return Result.success("文章已从回收站恢复");
    }

    /**
     * 永久删除文章
     */
    @RequiredRoles({"user"})
    @DeleteMapping("/article/recycle/permanently/{id}")
    public Result<String> permanentlyDelete(@PathVariable Long id, @RequestHeader("token") String token) {
        log.info("永久删除文章");
        Long userId = jwtTokenController.getUserId(token);
        articleService.permanentlyDelete(id, userId);
        return Result.success("文章已永久删除");
    }

    /**
     * 获取回收站中的文章列表
     */
    @RequiredRoles({"user"})
    @GetMapping("/article/recycle")
    public Result<List<Article>> getArticlesInRecycleBin(@RequestHeader("token") String token) {
        log.info("获取回收站中的文章列表");
        Long userId = jwtTokenController.getUserId(token);
        List<Article> articles = articleService.getArticlesInRecycleBin(userId);
        return Result.success(articles);
    }

    /**
     * 清理超过7天的回收站文章
     */
    @RequiredRoles({"admin"}) // 只有管理员可以执行此操作
    @DeleteMapping("/article/recycle/clean")
    public Result<String> cleanExpiredRecycleBinArticles() {
        log.info("清理超过7天的回收站文章");
        articleService.cleanExpiredRecycleBinArticles();
        return Result.success("超过7天的回收站文章已清理");
    }

    /**
     * 点赞文章
     * 结果存到redis中
     */
    @RequiredRoles({"user"})
    @PutMapping("/articles/{articleId}/likes")
    public Result<String> likeArticle(@PathVariable Long articleId) {
        log.info("用户点赞文章 {}", articleId);
        articleService.incrementArticleLikes(articleId);
        return Result.success("文章点赞成功");
    }

    /**
     * 取消点赞文章
     */
    @RequiredRoles({"user"})
    @PutMapping("/articles/{articleId}/unlikes")
    public Result<String> unlikeArticle(@PathVariable Long articleId) {
        log.info("用户取消点赞文章 {}", articleId);
        articleService.decrementArticleLikes(articleId);
        return Result.success("文章取消点赞成功");
    }

    /**
     * 获取文章点赞量

     */
    @RequiredRoles({"user"})
    @GetMapping("/articles/{articleId}/likes/count")
    public Result<Long> getArticleLikes(@PathVariable Long articleId) {
        log.info("获取文章 {} 的点赞量", articleId);
        Long likes = articleService.getArticleLikes(articleId);
        return Result.success(likes);
    }

    /**
     * 获取文章访问量
     */
    @RequiredRoles({"user"})
    @GetMapping("/articles/{articleId}/views/count")
    public Result<Long> getArticleViews(@PathVariable Long articleId) {
        log.info("获取文章 {} 的访问量", articleId);
        Long views = articleService.getArticleViews(articleId);
        return Result.success(views);
    }


    /**
     * 添加分类
     */
    @RequiredRoles({"admin"})
    @PostMapping("/categories")
    public Result<String> insertCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类");
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        articleService.insertCategory(category);
        return Result.success("添加成功");
    }

    /**
     * 获取子分类
     */
    @GetMapping("/categories/{parentId}")
    public Result<List<CategoryVO>> getSubCategories(@PathVariable Long parentId) {
        log.info("查询分类 ID 为 {} 的所有子分类", parentId);
        List<CategoryVO> subCategories = articleService.getSubCategories(parentId);
        return Result.success(subCategories);
    }

    /**
     * 获取待审核的文章列表
     * 用redis获得，带内容预览
     */
    @RequiredRoles({"admin"})
    @GetMapping("/pending")
    public List<PendingVO> getPendingArticles() {
        log.info("获取待审核的文章列表");
        return articleService.getPendingArticles();
    }

    /**
     * 审核文章
     * 通过，并发消息通知文章通过了
     */
    @RequiredRoles({"admin"})
    @PostMapping("/pending/approve/{id}")
    public Result<String> approveArticle(@PathVariable Long id) {
        articleService.reviewArticle(id, 2,"恭喜文章通过");// 调用审核逻辑，状态设为"approved"
        articleService.removePendingArticle(id);
        return Result.success("审核通过");
    }

    /**
     * 审核文章
     * 拒绝,并发消息通知文章被拒绝了
     */
    @RequiredRoles({"admin"})
    @PostMapping("/pending/reject/{id}")
    public Result<String> rejectArticle(@PathVariable Long id, @RequestBody SystemMessage systemMessage) {
        articleService.reviewArticle(id, 3,  systemMessage.getContent());// 调用审核逻辑，状态设为"rejected"
        articleService.removePendingArticle(id);
        return Result.success("审核拒绝");
    }

    /**
     * 批量审核通过
     */
    @RequiredRoles({"admin"})
    @PostMapping("/approve/batch")
    public Result<String> batchApproveArticles(@RequestBody List<Long> ids) {
        articleService.batchReviewArticles(ids, 2);
        return Result.success("批量审核通过");
    }

    /**
     * 批量审核拒绝
     */
    @RequiredRoles({"admin"})
    @PostMapping("/reject/batch")
    public Result<String> batchRejectArticles(@RequestBody List<Long>ids) {
        articleService.batchReviewArticles(ids, 3);
        return Result.success("批量审核拒绝");
    }

}

 