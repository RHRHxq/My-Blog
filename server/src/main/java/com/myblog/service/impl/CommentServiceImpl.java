package com.myblog.service.impl;

import com.myblog.entity.Comment;
import com.myblog.entity.CommentReport;
import com.myblog.mapper.CommentMapper;
import com.myblog.service.CommentService;
import com.myblog.vo.CommentReportVO;
import com.myblog.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    private static final String COMMENT_LIKES_KEY = "comment_likes";
    public void insertComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    public List<CommentVO> getCommentsByArticleId(Long articleId) {
        return commentMapper.getCommentsByArticleId(articleId);
    }

    public void deleteComment(Long id) {
        commentMapper.deleteComment(id);
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 增加评论点赞量
     * @param commentId 评论 ID
     */
    public void incrementCommentLikes(Long commentId) {
        redisTemplate.opsForHash().increment(COMMENT_LIKES_KEY, commentId.toString(), 1);
    }

    /**
     * 减少评论点赞量
     * @param commentId 评论 ID
     */
    public void decrementCommentLikes(Long commentId) {
        redisTemplate.opsForHash().increment(COMMENT_LIKES_KEY, commentId.toString(), -1);
    }

    /**
     * 获取评论点赞量
     * @param commentId 评论 ID
     * @return 评论点赞量
     */
    public Long getCommentLikes(Long commentId) {
        Object likes = redisTemplate.opsForHash().get(COMMENT_LIKES_KEY, commentId.toString());
        return likes != null ? Long.parseLong(likes.toString()) : 0;
    }

    // 新增方法，同步评论点赞量到数据库
    public void updateCommentLikes() {
        Map<Object, Object> commentLikes = redisTemplate.opsForHash().entries(COMMENT_LIKES_KEY);

        for (Map.Entry<Object, Object> entry : commentLikes.entrySet()) {
            Long commentId = Long.parseLong(entry.getKey().toString());
            Long likes = Long.parseLong(entry.getValue().toString());

            // 调用 Mapper 方法更新数据库中的评论点赞量
            commentMapper.updateCommentLikes(commentId, likes);
        }
    }

    public void addCommentReport(CommentReport commentReport) {
        commentMapper.addCommentReport(commentReport);
    }

    public List<CommentReportVO> getPendingCommentReports() {
        return commentMapper.getPendingCommentReports();
    }

    public void processCommentReport(Long id, int i) {
        commentMapper.processCommentReport(id, i);
    }
}
