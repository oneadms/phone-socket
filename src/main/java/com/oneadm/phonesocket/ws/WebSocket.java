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
    List<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) {

        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") Integer userId) {
        sessions.remove(session);

    }
    @OnMessage
    public void onMessage(Session session,String message) {
        session.getAsyncRemote().sendText(message);
    }



    @OnError
    public void onError(Session session, Throwable throwable) {

        log.info("websocket error", throwable);
    }


}
