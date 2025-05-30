package com.myblog.mapper;

import com.myblog.dto.ArticleTopicDTO;
import com.myblog.dto.UserFollowDTO;
import com.myblog.dto.UserLoginDTO;
import com.myblog.entity.*;
import com.myblog.vo.PrivateMessageVO;
import com.myblog.vo.SystemMessageVO;
import com.myblog.vo.UserFollowVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    // @Select("select * from user where username = #{username} ")
    User getByUsername(String username);

    /**
     * 注册
     * @param user
     */
    @Insert("insert into user(username, password, create_time,salt) values(#{username}, #{password}, #{createTime}, #{salt})")
    void register(User user);


    /**
     * 根据用户 ID 查询用户的角色列表
     * @param userId 用户 ID
     * @return 角色列表
     */
    @Select("SELECT r.name FROM role r " +
            "JOIN user_role ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<String> getRolesByUserId(Long userId);

    @Select("SELECT * FROM system_message WHERE user_id = #{userid}")
    List<SystemMessageVO> getSystemMessageById(Long userid);

    @Insert("insert into topic(name, description, status, user_id) values(#{name}, #{description}, #{status}, #{userId})")
    void addTopic(Topic topic);

    @Insert("insert into article_topic(article_id, topic_id) values(#{articleId}, #{topicId})")
    void addarticleTopic(ArticleTopicDTO articleTopicDTO);

    @Select("select * from article where id in (select id from article_topic where topic_id = #{id})")
    List<Article> getArticlesByTopicId(Long id);

    @Insert("insert into system_message(user_id, title, content, type, status, create_time) values(#{userId}, #{title}, #{content}, #{type}, #{status}, #{createTime})")
    void insertSystemMessage(SystemMessage systemMessage);

    @Update("update system_message set status = 1 where user_id = #{userid}")
    void updateSystemMessageStatus(Long userid);

    @Select("SELECT * FROM system_message WHERE user_id = #{userid} AND status = 0")
    List<SystemMessageVO> getUnreadSystemMessageById(Long userid);

    @Insert("insert into user_follow(user_id, followed_user_id,create_time) values(#{userId}, #{followedUserId},#{createTime})")
    void follow(UserFollow userFollow);

    @Select("select * from user_follow where user_id = #{userId}")
    List<UserFollowVO> getFollowedList(Long userId);

    @Select("select * from user_follow where followed_user_id = #{followedUserId}")
    List<UserFollowVO> getFollowerList(Long followedId);

    @Insert("insert into private_message(sender_id, receiver_id, content, create_time) values(#{senderId}, #{receiverId}, #{content}, #{createTime})")
    void sendMessage(PrivateMessage privateMessage);

    @Select("select * from private_message where (sender_id = #{senderId} and receiver_id = #{receiverId}) or (sender_id = #{receiverId} and receiver_id = #{senderId})")
    List<PrivateMessageVO> getConversation(Long senderId, Long receiverId);

    @Select("select * from user where username = #{userName}")
    UserLoginDTO getUser(String userName);
}
