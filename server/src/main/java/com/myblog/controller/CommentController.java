package com.myblog.controller;

import com.myblog.config.RequiredRoles;
import com.myblog.dto.CommentDTO;
import com.myblog.dto.CommentReportDTO;
import com.myblog.entity.Comment;
import com.myblog.entity.CommentReport;
import com.myblog.result.Result;
import com.myblog.service.CommentService;
import com.myblog.vo.CommentReportVO;
import com.myblog.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequiredRoles({"user"})
    @PostMapping("/comment")
    public Result<String> addComment(@RequestBody CommentDTO commentDTO) {
        log.info("添加评论：{}", commentDTO);
        Comment comment  = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setStatus(0);
        comment.setLikes(0);
        comment.setCreateTime(LocalDateTime.now());
        commentService.insertComment(comment);
        return Result.success("评论添加成功");
    }

    @RequiredRoles({"user"})
    @GetMapping("/{articleId}")
    public Result<List<CommentVO>> getCommentsByArticleId(@PathVariable Long articleId) {
        log.info("获取文章 {} 的评论", articleId);
        List<CommentVO> comments = commentService.getCommentsByArticleId(articleId);
        return Result.success(comments);
    }

    @RequiredRoles({"user"})
    @DeleteMapping("/comment/{id}")
    public Result<String> deleteComment(@PathVariable Long id) {
        log.info("删除评论：{}", id);
        commentService.deleteComment(id);
        return Result.success("删除成功");
    }

    /**
     * 点赞评论,redis
     */
    @RequiredRoles({"user"})
    @PutMapping("/comments/{commentId}/likes")
    public Result<String> likeComment(@PathVariable Long commentId) {
        log.info("用户点赞评论 {}", commentId);
        commentService.incrementCommentLikes(commentId);
        return Result.success("评论点赞成功");
    }

    /**
     * 取消点赞评论
     */
    @RequiredRoles({"user"})
    @DeleteMapping("/comments/{commentId}/unlikes")
    public Result<String> unlikeComment(@PathVariable Long commentId) {
        log.info("用户取消点赞评论 {}", commentId);
        commentService.decrementCommentLikes(commentId);
        return Result.success("评论取消点赞成功");
    }

    /**
     * 获取评论点赞量
     */
    @GetMapping("/comments/{commentId}/likes/count")
    public Result<Long> getCommentLikes(@PathVariable Long commentId) {
        log.info("获取评论 {} 的点赞量", commentId);
        Long likes = commentService.getCommentLikes(commentId);
        return Result.success(likes);
    }

    @RequiredRoles({"user"})
    @PostMapping("/comment/report")
    public Result<String> addCommentReport(@RequestBody CommentReportDTO commentReportDTO) {
        CommentReport commentReport = new CommentReport();
        BeanUtils.copyProperties(commentReportDTO, commentReport);
        commentReport.setStatus(0);
        commentReport.setCreateTime(LocalDateTime.now());
        log.info("添加评论举报：{}", commentReport);
        commentService.addCommentReport(commentReport);
        return Result.success("评论举报添加成功");
    }

    /**
     * 获取待处理的评论举报列表
     * @return 待处理的评论举报列表
     */
    @RequiredRoles({"admin"})
    @GetMapping("/comment/report")
    public Result<List<CommentReportVO>> getPendingCommentReports() {
        log.info("获取待处理的评论举报列表");
        List<CommentReportVO> pendingReports = commentService.getPendingCommentReports();
        return Result.success(pendingReports);
    }

    /**
     * 处理评论举报
     * @param id 举报ID
     * @return 操作结果
     */
    @RequiredRoles({"admin"})
    @PutMapping("/comment/report/{id}/process")
    public Result<String> processCommentReport(@PathVariable Long id) {
        log.info("处理评论举报，ID：{}，状态：{}", id, 1);
        commentService.processCommentReport(id, 1);
        return Result.success("评论举报处理成功");
    }
}
