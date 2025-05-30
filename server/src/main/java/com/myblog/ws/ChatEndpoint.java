package com.myblog.ws;

import com.alibaba.fastjson.JSON;
import com.myblog.config.GetHttpSessionConfig;
import com.myblog.entity.Message;
import com.myblog.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfig.class)
@Component
@Slf4j
public class ChatEndpoint {

    private static final Map<Long,Session> onLinerUsers=new ConcurrentHashMap<>();

    private HttpSession  httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){

       this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
       Long userId = (Long) this.httpSession.getAttribute("userId");
       log.info("获取到的用户 ID: {}", userId);
       log.info("连接");
       onLinerUsers.put(userId,session);

        String message = MessageUtils.getMessage(true,null,getFriends());
        broadcastAllUsers(message);
    }

    public Set getFriends(){
        Set<Long> set=onLinerUsers.keySet();
        return set;
    }

    public void broadcastAllUsers(String message){
        try {
            Set<Map.Entry<Long, Session>> entries = onLinerUsers.entrySet();
            for (Map.Entry<Long, Session> entry : entries) {
                Session session = entry.getValue();
                session.getBasicRemote().sendText(message);
            }
        }  catch (Exception e) {
        }
    }

    @OnMessage
    public void onMessage(String message){

        try{
            Message msg = JSON.parseObject(message, Message.class);
            Long toUserId = msg.getToUserId();
            String mess=msg.getMessage();
            Session session = onLinerUsers.get(toUserId);
            Long userId = (Long) this.httpSession.getAttribute("userId");
            String msg1=MessageUtils.getMessage(false,userId,mess);
            session.getBasicRemote().sendText(msg1);
        }catch (Exception e){

        }

    }
    @OnClose
    public void onClose(Session session){

        Long  userId = (Long) this.httpSession.getAttribute("userId");
        onLinerUsers.remove(userId);
        String message = MessageUtils.getMessage(true,null,getFriends());
        broadcastAllUsers(message);

    }

}
