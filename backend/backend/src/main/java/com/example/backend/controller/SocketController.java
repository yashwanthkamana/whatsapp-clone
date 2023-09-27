package com.example.backend.controller;

import com.example.backend.entity.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketController extends TextWebSocketHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getId() + "  joined");
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(session.getId() + " :  " + message.toString());
        String json = (String) message.getPayload();
        try {
            ChatMessage chatMessage = objectMapper.readValue(json, ChatMessage.class);
            System.out.println(chatMessage.getName() + "   " + chatMessage.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "  closed");
    }
}
