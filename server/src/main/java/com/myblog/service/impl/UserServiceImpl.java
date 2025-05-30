package com.myblog.service.impl;

import com.myblog.constant.MessageConstant;
import com.myblog.dto.*;
import com.myblog.entity.*;
import com.myblog.exception.PasswordErrorException;
import com.myblog.mapper.UserMapper;
import com.myblog.service.UserService;
import com.myblog.vo.PrivateMessageVO;
import com.myblog.vo.SystemMessageVO;
import com.myblog.vo.UserFollowVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.myblog.exception.AccountNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 员工登录
     */
    public User login(UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.getByUsername(username);
        String salt = user.getSalt();
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 2、将盐值和加密的密码共同返回（合并盐值和加密密码）
        // 总共 65 位
        log.info("hello");
        //2、处理各种异常情况（用户名不存在、密码不对)
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 后期需要进行md5加密，然后再进行比对
        if (!finalPassword.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }


        //3、返回实体对象
        return user;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        log.info("用户注册：{}", userRegisterDTO);
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println("盐值：" + salt);
        // 使用（盐值+明文密码）得到加密的密码
        // getBytes() 括号里设置编码 因为盐值没有中文，所以这里无需设置utf8
        // 此处加密后的密码是 32位
        String finalPassword = DigestUtils.md5DigestAsHex((salt + user.getPassword()).getBytes());
        // 将盐值和加密的密码共同返回（合并盐值和加密密码）
        // 总共 65 位
        user.setSalt(salt);
        user.setPassword(finalPassword);
        userMapper.register(user);
    }

    public List<String> getRolesByUserId(Long id) {
        return userMapper.getRolesByUserId(id);
    }

    public void insertSystemMessage(SystemMessage systemMessage) {
        log.info("添加用户后台信息");
        systemMessage.setCreateTime(LocalDateTime.now());
        userMapper.insertSystemMessage(systemMessage);
    }

    public List<SystemMessageVO> getSystemMessageById(Long userid) {
        log.info("获取用户后台信息");
        List<SystemMessageVO> systemMessageVOList = userMapper.getSystemMessageById(userid);
        userMapper.updateSystemMessageStatus(userid);
        return systemMessageVOList;
    }

    public String getUnreadSystemMessageByUserId(Long userid) {
        log.info("获取用户未读后台信息数量");
        List<SystemMessageVO> unreadSystemMessageVOList = userMapper.getUnreadSystemMessageById(userid);
        return "您有"+unreadSystemMessageVOList.size()+"条未读信息";
    }

    public void addTopic(TopicDTO topicDTO) {
        log.info("添加话题");
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO, topic);
        topic.setCreateTime(LocalDateTime.now());
        userMapper.addTopic(topic);
    }

    public void addArticleTopic(ArticleTopicDTO articleTopicDTO) {
        log.info("添加文章进专题");
        userMapper.addarticleTopic(articleTopicDTO);
    }

    public List<Article> getArticlesByTopicId(Long id) {
        log.info("根据专题id获取文章");
        List<Article> articles = userMapper.getArticlesByTopicId(id);
        return articles;
    }

    public void follow(UserFollow userFollow) {
        log.info("关注用户");
        userMapper.follow(userFollow);
    }

    public List<UserFollowVO> getFollowedList(Long userId) {
        log.info("获取关注列表");
        List<UserFollowVO> followedList = userMapper.getFollowedList(userId);
        return followedList;
    }

    public List<UserFollowVO> getFollowerList(Long followedUserId) {
        log.info("获取粉丝列表");
        List<UserFollowVO> followerList = userMapper.getFollowerList(followedUserId);
        return followerList;
    }

    public void sendMessage(PrivateMessage privateMessage) {
        log.info("发送私信");
        userMapper.sendMessage(privateMessage);
    }

    public List<PrivateMessageVO> getConversation(Long senderId, Long receiverId) {
        log.info("获取私信");
        List<PrivateMessageVO> conversation = userMapper.getConversation(senderId, receiverId);
        return conversation;
    }

    public UserLoginDTO getUser(String userName) {
        log.info("比对用户信息");
        UserLoginDTO userLoginDTO = userMapper.getUser(userName);
        return userLoginDTO;
    }
}
