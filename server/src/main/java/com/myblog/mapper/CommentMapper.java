package com.myblog.mapper;

import com.myblog.entity.Comment;
import com.myblog.entity.CommentReport;
import com.myblog.vo.CommentReportVO;
import com.myblog.vo.CommentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {


    @Insert("insert into comment (article_id, parent_id, user_id, content, status, likes, create_time,reply_to) values (#{articleId}, #{parentId}, #{userId}, #{content}, #{status}, #{likes}, #{createTime},#{replyTo})")
    void insertComment(Comment comment);

    @Select("select * from comment where article_id = #{articleId}")
    List<CommentVO> getCommentsByArticleId(Long articleId);

    @Delete("delete from comment where id = #{id}")
    void deleteComment(Long id);

    @Update("update comment set likes = #{likes} where id = #{commentId}")
    void updateCommentLikes(Long commentId, Long likes);

    @Insert("insert into comment_report (comment_id, user_id, reason, status, create_time) values (#{commentId}, #{userId}, #{reason}, #{status}, #{createTime})")
    void addCommentReport(CommentReport commentReport);

    @Select("select * from comment_report where status = 0")
    List<CommentReportVO> getPendingCommentReports();

    @Update("update comment_report set status = #{i} where id = #{id}")
    void processCommentReport(Long id, int i);
}
