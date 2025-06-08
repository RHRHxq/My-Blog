package com.myblog.controller;

import cn.hutool.db.Session;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myblog.config.RequiredRoles;
import com.myblog.constant.JwtClaimsConstant;
import com.myblog.dto.*;
import com.myblog.entity.*;
import com.myblog.properties.JwtProperties;
import com.myblog.result.Result;
import com.myblog.service.UserService;
import com.myblog.utils.AliOssUtil;
import com.myblog.utils.JwtUtil;
import com.myblog.vo.PrivateMessageVO;
import com.myblog.vo.SystemMessageVO;
import com.myblog.vo.UserFollowVO;
import com.myblog.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        List<String> roles = userService.getRolesByUserId(user.getId());

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ADMIN_ID, user.getId());
        String rolesStr = String.join(",", roles);
        claims.put("roles", rolesStr);

        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .token(token)
                .build();

        userLoginVO.setReminder(userService.getUnreadSystemMessageByUserId(user.getId()));

        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        return Result.success(userLoginVO);
    }

    @GetMapping("/login/github/callback")
    public void getCode(String code,HttpServletRequest request) throws JsonProcessingException {
        log.info("获取用户信息:{}", code);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("client_id", "Ov23liaF4dVjzYMWaVKw");
        paramMap.put("client_secret", "c145d988901cb4b617a9ebd381ec0aa64f2575f6");
        paramMap.put("code", code);

//                 发送请求，显式设置 Accept 头为 JSON
        String result = HttpRequest.post("https://github.com/login/oauth/access_token")
                .header("Accept", "application/json")
                .form(paramMap)
                .execute()
                .body();

        log.info("GitHub 响应：{}", result);

        // 解析 JSON 响应
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> tokenResponse = objectMapper.readValue(result, Map.class);
        String token = tokenResponse.get("access_token"); // 正确获取 token
        log.info("获取用户令牌：{}", token);

        // 获取用户信息
        String userInfo = HttpRequest.get("https://api.github.com/user")
                .header("Authorization", "token " + token)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .execute()
                .body();
        log.info("获取用户信息：{}", userInfo);
//        paramMap.put("accept", "json");
//        String result = HttpUtil.post("https://github.com/login/oauth/access_token", paramMap);
////        log.info("获取result：{}", result);
//        String token = result.split("&")[0].split("=")[1];
//        log.info("获取用户令牌：{}", token);
//        // 获取用户信息
//        String finalResult = HttpRequest.get("https://api.github.com/user")
//                .header("Authorization", "token " + token)
//                .header("X-GitHub-Api-Version", "2022-11-28")
//                .execute().body();
//        log.info("获取用户信息：{}", finalResult);
        GitHubUser gitHubUser = new ObjectMapper().readValue(userInfo, GitHubUser.class);
        if (userService.getUser(gitHubUser.getLogin()) == null) {
            userService.register(new UserRegisterDTO(gitHubUser.getLogin(), "1234"));
            login(new UserLoginDTO(gitHubUser.getLogin(), "1234"),request);
        }
        else {
            login(new UserLoginDTO(gitHubUser.getLogin(), "1234"),request);
        }

    }


    /**
     * 用户注册
     * @param userRegisterDTO
     */

    @PostMapping("/register")
    public Result<String> register(@RequestBody UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}",userRegisterDTO);
        userService.register(userRegisterDTO);
        return Result.success();
    }
    @RequiredRoles({"user"})
    @PostMapping("/logout")
    public Result<String> logout(){
        log.info("用户退出");
        return Result.success("退出成功");
    }

    @GetMapping("/SystemMessage/{userId}")
    public Result<List<SystemMessageVO>> getSystemMessage(@PathVariable Long userId){
        log.info("获取用户后台信息");
        return Result.success(userService.getSystemMessageById(userId));
    }

    @RequiredRoles({"user"})
    @PostMapping("/topic")
    public Result<String> addTopic(@RequestBody TopicDTO topicDTO) {
        log.info("添加专题：{}", topicDTO);
        userService.addTopic(topicDTO);
        return Result.success();
    }

    @RequiredRoles({"user"})
    @PostMapping("/topic/link")
    public Result<String> addArticleTopic(@RequestBody ArticleTopicDTO articleTopicDTO) {
        log.info("添加文章进入专题链接");
        userService.addArticleTopic(articleTopicDTO);
        return Result.success();
    }

    @RequiredRoles({"user"})
    @GetMapping("/topic/{id}")
    public Result<List<Article>> getArticlesByTopicId(@PathVariable Long id) {
        log.info("获取专题下的文章");
        return Result.success(userService.getArticlesByTopicId(id));
    }

    @RequiredRoles({"user"})
    @PostMapping("/follow")
    public Result<String> follow(@RequestBody UserFollowDTO userFollowDTO) {
        log.info("用户关注：{}", userFollowDTO);
        UserFollow userFollow=new UserFollow();
        BeanUtils.copyProperties(userFollowDTO,userFollow);
        userFollow.setCreateTime(LocalDateTime.now());
        userService.follow(userFollow);
        return Result.success("关注成功");
    }

    /**
     * 获取用户的关注列表
     * @param userId 用户ID
     * @return 关注列表
     */
    @GetMapping("/follow/followed/{userId}")
    public Result<List<UserFollowVO>> getFollowedList(@PathVariable Long userId) {
        log.info("获取用户 {} 的关注列表", userId);
        List<UserFollowVO> followedList = userService.getFollowedList(userId);
        return Result.success(followedList);
    }

    /**
     * 获取用户的粉丝列表
     * @param followedUserId 被关注用户ID
     * @return 粉丝列表
     */

    @GetMapping("/follow/follower/{followedUserId}")
    public Result<List<UserFollowVO>> getFollowerList(@PathVariable Long followedUserId) {
        log.info("获取用户 {} 的粉丝列表", followedUserId);
        List<UserFollowVO> followerList = userService.getFollowerList(followedUserId);
        return Result.success(followerList);
    }

    @RequiredRoles({"user"})
    @PostMapping("/message")
    public Result<String> sendMessage(@RequestBody PrivateMessageDTO privateMessageDTO) {
        log.info("用户发送私信：{}", privateMessageDTO);
        PrivateMessage privateMessage = new PrivateMessage();
        BeanUtils.copyProperties(privateMessageDTO, privateMessage);
        privateMessage.setCreateTime(LocalDateTime.now());
        userService.sendMessage(privateMessage);
        return Result.success("私信发送成功");
    }

    /**
     * 获取两个用户之间的私信对话
     * @param senderId 发送者ID
     * @param receiverId 接收者ID
     * @return 私信列表
     */
    @RequiredRoles({"user"})
    @GetMapping("/message/{senderId}/{receiverId}")
    public Result<List<PrivateMessageVO>> getConversations(@PathVariable Long senderId, @PathVariable Long receiverId) {
        log.info("获取用户 {} 和用户 {} 的聊天记录", senderId, receiverId);
        List<PrivateMessageVO> conversation = userService.getConversations(senderId, receiverId);
        return Result.success(conversation);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public Result<List<ChatMessage>> getConversation(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<ChatMessage> conversation = userService.getConversation(senderId, receiverId);
        return Result.success(conversation);
    }
}
