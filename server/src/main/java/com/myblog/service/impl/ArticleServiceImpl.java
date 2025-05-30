package com.myblog.service.impl;

import com.myblog.dto.SystemMessageDTO;
import com.myblog.entity.Article;
import com.myblog.entity.Category;
import com.myblog.entity.ReviewNotification;
import com.myblog.entity.SystemMessage;
import com.myblog.mapper.ArticleMapper;
import com.myblog.mapper.UserMapper;
import com.myblog.service.ArticleService;
import com.myblog.vo.ArticleVO;
import com.myblog.vo.CategoryVO;
import com.myblog.vo.PendingVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    // Redis key前缀
    private static final String PENDING_ARTICLES_KEY = "pending_articles";
    private static final String ARTICLE_INFO_PREFIX = "article:";
    private static final String ARTICLE_VIEWS_KEY = "article_views";
    private static final String ARTICLE_LIKES_KEY = "article_likes";

    @Autowired
    private ArticleMapper articleMapper;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
//    @Value("${queue.article_submission}")
//    private String submissionQueue;

    /**
     * 根据筛选条件获取文章
     * @param categoryId 分类id
     * @param tagId 标签id
     * @param publishTime 发布时间
     * @param sortField 排序字段
     * @param sortOrder 排序顺序
     * @return
     */
    public List<ArticleVO> getArticlesByFilterAndSort(Long categoryId, Long tagId, String publishTime, String sortField, String sortOrder) {
        log.info("根据筛选条件获取文章：categoryId={}, tagId={}, publishTime={}, sortField={}, sortOrder={}", categoryId, tagId, publishTime, sortField, sortOrder);
        return articleMapper.getArticlesByFilterAndSort(categoryId, tagId, publishTime, sortField, sortOrder);
    }

    public void insertArticle(Article article) {
        log.info("插入文章：{}", article);
        articleMapper.insert(article);

    }

    public void deleteArticle(Long id, Long userId) {
        log.info("删除文章：id={}, userId={}", id, userId);
        articleMapper.deleteByIdAndUserId(id, userId);
    }

    public void updateArticle(Article article) {
        log.info("更新文章：{}", article);
        articleMapper.updateById(article);
    }

    public Article selectArticle(Long Id) {
        log.info("根据id获取文章：Id={}", Id);
        Article article = articleMapper.selectById(Id);
        return article;
    }

    public List<Article> getDraftArticles() {
        log.info("获取草稿文章");
        return articleMapper.getDraftArticles();
    }

    public void addDraft(Article article) {
        log.info("添加草稿：{}", article);
        articleMapper.insert(article);
    }


    public void moveToRecycleBin(Long id, Long userId) {
        articleMapper.moveToRecycleBin(id, userId);
    }


    public void restoreFromRecycleBin(Long id, Long userId) {
        articleMapper.restoreFromRecycleBin(id, userId);
    }


    public void permanentlyDelete(Long id, Long userId) {
        articleMapper.permanentlyDelete(id, userId);
    }


    public List<Article> getArticlesInRecycleBin(Long userId) {
        return articleMapper.getArticlesInRecycleBin(userId);
    }


    public void cleanExpiredRecycleBinArticles() {
        articleMapper.cleanExpiredRecycleBinArticles();
    }




    /**
     * 增加文章点赞量
     * @param articleId 文章 ID
     */
    public void incrementArticleLikes(Long articleId) {
        redisTemplate.opsForHash().increment(ARTICLE_LIKES_KEY, articleId.toString(), 1);
    }

    /**
     * 减少文章点赞量
     * @param articleId 文章 ID
     */
    public void decrementArticleLikes(Long articleId) {
        redisTemplate.opsForHash().increment(ARTICLE_LIKES_KEY, articleId.toString(), -1);
    }

    /**
     * 获取文章点赞量
     * @param articleId 文章 ID
     * @return 文章点赞量
     */
    public Long getArticleLikes(Long articleId) {
        Object likes = redisTemplate.opsForHash().get(ARTICLE_LIKES_KEY, articleId.toString());
        return likes != null ? Long.parseLong(likes.toString()) : 0;
    }

    /**
     * 增加文章访问量
     * @param articleId 文章 ID
     */
    public void incrementArticleViews(Long articleId) {
        redisTemplate.opsForHash().increment(ARTICLE_VIEWS_KEY, articleId.toString(), 1);
    }

    /**
     * 获取文章访问量
     * @param articleId 文章 ID
     * @return 文章访问量
     */
    public Long getArticleViews(Long articleId) {
        Object views = redisTemplate.opsForHash().get(ARTICLE_VIEWS_KEY, articleId.toString());
        return views != null ? Long.parseLong(views.toString()) : 0;
    }

    public void updateArticleViews() {
        Map<Object, Object> articleViews = redisTemplate.opsForHash().entries("article_views");

        for (Map.Entry<Object, Object> entry : articleViews.entrySet()) {
            Long articleId = Long.parseLong(entry.getKey().toString());
            Long views = Long.parseLong(entry.getValue().toString());

            // 更新数据库中的文章访问量
            articleMapper.updateArticleViews(articleId, views);
        }
    }

    public void updateArticleLikes() {
        Map<Object, Object> articleLikes = redisTemplate.opsForHash().entries("article_likes");

        for (Map.Entry<Object, Object> entry : articleLikes.entrySet()) {
            Long articleId = Long.parseLong(entry.getKey().toString());
            Long likes = Long.parseLong(entry.getValue().toString());

            // 更新数据库中的文章点赞量
            articleMapper.updateArticleLikes(articleId, likes);
        }
    }

    public void insertCategory(Category category) {
        log.info("插入分类：{}", category);
        articleMapper.insertCategory(category);
    }

    public List<CategoryVO> getSubCategories(Long parentId) {
        log.info("查询分类 ID 为 {} 的所有子分类", parentId);
        return articleMapper.getSubCategories(parentId);
    }

    public void addPendingArticle(Article article) {
        log.info("添加待审核文章：{}", article);
        if (article.getSubmitTime() == null) {
            article.setSubmitTime(LocalDateTime.now()); // 设置当前时间为默认提交时间
        }
        // 将LocalDateTime转换为时间戳作为分数
        long timestamp = article.getSubmitTime()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

        redisTemplate.opsForZSet().add(
                PENDING_ARTICLES_KEY,
                article.getId(),
                timestamp
        );

        String articleKey = ARTICLE_INFO_PREFIX + article.getId();
        redisTemplate.opsForHash().put(articleKey, "title", article.getTitle());
        redisTemplate.opsForHash().put(articleKey, "author", article.getUserId());
        redisTemplate.opsForHash().put(articleKey, "previewContent", article.getContent());

        // 存储LocalDateTime对象（通过toString方法转为字符串）
        redisTemplate.opsForHash().put(articleKey, "submitTime", article.getSubmitTime().toString());
    }

    public List<PendingVO> getPendingArticles() {
        List<PendingVO> articles = new ArrayList<>();

        // 获取所有待审文章ID（按时间倒序）
        Set<Object> articleIds = redisTemplate.opsForZSet().reverseRange(
                PENDING_ARTICLES_KEY, 0, -1
        );

        if (articleIds != null && !articleIds.isEmpty()) {
            for (Object articleId : articleIds) {
                String articleKey = ARTICLE_INFO_PREFIX + articleId;

                PendingVO article = new PendingVO();
                article.setId(Long.parseLong(articleId.toString()));
                article.setTitle((String) redisTemplate.opsForHash().get(articleKey, "title"));
                article.setUserId(Long.parseLong(redisTemplate.opsForHash().get(articleKey, "author").toString()));
                article.setContent((String) redisTemplate.opsForHash().get(articleKey, "previewContent"));

                // 从字符串转换回LocalDateTime对象
                String submitTimeStr = (String) redisTemplate.opsForHash().get(articleKey, "submitTime");
                if (submitTimeStr != null) {
                    article.setSubmitTime(LocalDateTime.parse(submitTimeStr));
                }

                articles.add(article);
            }
        }

        return articles;
    }

    public void reviewArticle(Long id, int i, String reason){
        log.info("审核文章：{}", id);
        articleMapper.reviewArticle(id, i);
        if(i==3){
            SystemMessage  systemMessage = new SystemMessage();
            systemMessage.setUserId(articleMapper.selectById(id).getUserId());
            systemMessage.setTitle("文章审核拒绝");
            systemMessage.setContent(reason);
            systemMessage.setType(1);
            systemMessage.setCreateTime(LocalDateTime.now());
            systemMessage.setStatus(0);
            userMapper.insertSystemMessage(systemMessage);
        }
        else if(i==2){
            SystemMessage  systemMessage = new SystemMessage();
            systemMessage.setUserId(articleMapper.selectById(id).getUserId());
            systemMessage.setTitle("文章审核通过");
            systemMessage.setContent(reason);
            systemMessage.setType(1);
            systemMessage.setCreateTime(LocalDateTime.now());
            systemMessage.setStatus(0);
            userMapper.insertSystemMessage(systemMessage);
        }
    }

    public void removePendingArticle(Long articleId) {
        redisTemplate.opsForZSet().remove(PENDING_ARTICLES_KEY, articleId);

        // 删除对应的哈希数据
        String articleKey = ARTICLE_INFO_PREFIX + articleId;
        redisTemplate.delete(articleKey); // 直接删除Key，高效清理数据
    }

    public void batchReviewArticles(List<Long> articleIds, int i) {
        log.info("批量审核文章：{}", articleIds);
        for (Long articleId : articleIds) {
            if (i==2) {
                articleMapper.reviewArticle(articleId, 2);

            } else {
                articleMapper.reviewArticle(articleId, 3);
                SystemMessage  systemMessage = new SystemMessage();
                systemMessage.setUserId(articleMapper.selectById(articleId).getUserId());
                systemMessage.setTitle("文章审核拒绝");
                systemMessage.setContent("文章审核拒绝");
                systemMessage.setType(1);
                systemMessage.setCreateTime(LocalDateTime.now());
                systemMessage.setStatus(0);
                userMapper.insertSystemMessage(systemMessage);
            }
            removePendingArticle(articleId);
        }
    }

//    public void rejectArticle(Long id,  int i) {
//
//        ReviewNotification  reviewNotification = new ReviewNotification();
//        if (i == 3) { // 审核拒绝
//            // 获取文章信息
//            Article article = articleMapper.selectById(id);
//            Long userId = article.getUserId();
//
//            // 创建审核通知
//            ReviewNotification notification = new ReviewNotification();
//            notification.setArticleId(id);
//            notification.setUserId(userId);
//            notification.setStatus(i);
//
//            // 发送消息到对应的用户队列
//            String routingKey = "rejectionNotification.user." + userId;
//            rabbitTemplate.convertAndSend("rejectionNotification.exchange", routingKey, notification);
//        }
//
//    }

}
