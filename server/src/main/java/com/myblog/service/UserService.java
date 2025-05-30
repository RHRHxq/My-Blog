package com.myblog.service;

import com.myblog.dto.*;
import com.myblog.entity.Article;
import com.myblog.entity.PrivateMessage;
import com.myblog.entity.User;
import com.myblog.entity.UserFollow;
import com.myblog.vo.PrivateMessageVO;
import com.myblog.vo.SystemMessageVO;
import com.myblog.vo.UserFollowVO;

import java.util.List;

public interface UserService {
    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    User login(UserLoginDTO employeeLoginDTO);

    /**
     * 员工注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);


    /**
     * 根据用户id获取用户角色
     * @param id
     * @return
     */
    List<String> getRolesByUserId(Long id);

    /**
     * 获取用户后台消息
     * @return
     */
    List<SystemMessageVO> getSystemMessageById(Long userid);

    String getUnreadSystemMessageByUserId(Long userid);

    void addTopic(TopicDTO topicDTO);

    void addArticleTopic(ArticleTopicDTO articleTopicDTO);

    List<Article> getArticlesByTopicId(Long id);

    void follow(UserFollow userFollow);

    List<UserFollowVO> getFollowedList(Long userId);

    List<UserFollowVO> getFollowerList(Long followedId);

    void sendMessage(PrivateMessage privateMessage);

    List<PrivateMessageVO> getConversation(Long senderId, Long receiverId);


    UserLoginDTO getUser(String userName);
}
