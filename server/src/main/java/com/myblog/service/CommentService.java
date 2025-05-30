package com.myblog.service;


import com.myblog.entity.Comment;
import com.myblog.entity.CommentReport;
import com.myblog.vo.CommentReportVO;
import com.myblog.vo.CommentVO;

import java.util.List;

public interface CommentService {


    void insertComment(Comment comment);

    List<CommentVO> getCommentsByArticleId(Long articleId);

    void deleteComment(Long id);

    void incrementCommentLikes(Long commentId);

    void decrementCommentLikes(Long commentId);

    Long getCommentLikes(Long commentId);

    void updateCommentLikes();

    void addCommentReport(CommentReport commentReport);

    List<CommentReportVO> getPendingCommentReports();

    void processCommentReport(Long id, int i);
}
