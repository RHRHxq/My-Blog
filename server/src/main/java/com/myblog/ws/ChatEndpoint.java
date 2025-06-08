package com.myblog.ws;

import com.alibaba.fastjson.JSON;
import com.myblog.config.GetHttpSessionConfig;
import com.myblog.entity.ChatMessage;
import com.myblog.entity.Message;
import com.myblog.entity.User;
import com.myblog.service.UserService;
import com.myblog.util.MessageUtils;
import com.myblog.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/api/chat", configurator = GetHttpSessionConfig.class)
@Component
@Slf4j
public class ChatEndpoint {

    private static final Map<Long, Session> onlineUsers = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    private static final UserService userService = SpringContextUtils.getBean(UserService.class);


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        Long userId = (Long) this.httpSession.getAttribute("userId");
        log.info("获取到的用户 ID: {}", userId);
        log.info("连接");
        onlineUsers.put(userId, session);

        String message = MessageUtils.getMessage(true, null, getFriends());
        broadcastAllUsers(message);

    }

    public Set<Long> getFriends() {
        return onlineUsers.keySet();
    }

    public void broadcastAllUsers(String message) {
        try {
            Set<Map.Entry<Long, Session>> entries = onlineUsers.entrySet();
            for (Map.Entry<Long, Session> entry : entries) {
                Session session = entry.getValue();
                if (session != null && session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            }
        } catch (Exception e) {
            log.error("广播消息时出错", e);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            ChatMessage chatMessage = JSON.parseObject(message, ChatMessage.class);
            Long toUserId = chatMessage.getReceiverId();
            String mess = chatMessage.getMessage();
            Session session = onlineUsers.get(toUserId);
            Long userId = (Long) this.httpSession.getAttribute("userId");
            chatMessage.setSenderId(userId);
            userService.sendMessage(chatMessage);

            String msg1 = MessageUtils.getMessage(false, userId, mess);
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(msg1);
            }
        } catch (Exception e) {
            log.error("处理消息时出错", e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        Long userId = (Long) this.httpSession.getAttribute("userId");
        onlineUsers.remove(userId);
        String message = MessageUtils.getMessage(true, null, getFriends());
        log.info("关闭连接:{}",  userId);
        broadcastAllUsers(message);
    }
}
