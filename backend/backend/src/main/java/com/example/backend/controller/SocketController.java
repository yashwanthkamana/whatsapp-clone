package com.example.backend.controller;

import com.example.backend.entity.ChatMessage;
import com.example.backend.entity.MessageWrapper;
import com.example.backend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

import static com.example.backend.utils.Constants.*;

@Component
public class SocketController extends TextWebSocketHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    Map<String, WebSocketSession> userIdSessionMap = new HashMap<>();
    Set<User> userSet = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getId() + "  joined");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue((String) message.getPayload(), ChatMessage.class);
        switch (chatMessage.getType()) {
            case TYPE_REGISTER:
                userIdSessionMap.put(chatMessage.getId(), session);
                userSet.add(new User(chatMessage.getId(), chatMessage.getName()));
                session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(TYPE_PEER_INFO, userSet.toArray()))));
                break;
            case TYPE_PEER_CHAT:
                userIdSessionMap.get(chatMessage.getTo()).sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(TYPE_PEER_CHAT, chatMessage))));
                session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(TYPE_PEER_CHAT, chatMessage))));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chatMessage.getType());
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "  closed");
    }
}
