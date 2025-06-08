package com.myblog.config;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Slf4j
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator{

    @Override
    public  void modifyHandshake(ServerEndpointConfig sec,
                                  HandshakeRequest request,
                                  HandshakeResponse response)
    {
        if(request.getHttpSession() == null){
            log.info("request.getHttpSession() = null");
        }
        if(sec.getUserProperties()==null){
            log.info("sec.getUserProperties() = null");
        }
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
    }
}
