package com.oneadm.phonesocket.ws;

import cn.hutool.Hutool;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint("/ws/handler")
@Component
@Slf4j
public class WebSocket {
    static List<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        log.info("打开会话,当前会话数量：{}", sessions.size());
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("关闭会话,当前会话数量：{}", sessions.size());
        sessions.remove(session);

    }
    @OnMessage
    public void onMessage(Session session,String message) {
        log.info("收到一条消息,当前会话数量：{}", sessions.size());
        sessions.forEach(s->s.getAsyncRemote().sendText(message));
    }



    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("WebSocket异常,当前会话数量：{}", sessions.size());
        log.info("websocket error", throwable);
    }


}
