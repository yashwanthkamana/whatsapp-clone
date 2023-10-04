package com.example.backend.controller;

import com.example.backend.entity.ChatMessage;
import com.example.backend.entity.MessageWrapper;
import com.example.backend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.backend.utils.Constants.*;

@Component
@Slf4j
public class SocketController extends TextWebSocketHandler {
    private ObjectMapper objectMapper = new ObjectMapper();
    Map<String, WebSocketSession> userIdSessionMap = new HashMap<>();
    Set<User> userSet = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session.getId() + "  joined");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("in handle");
        ChatMessage chatMessage = objectMapper.readValue((String) message.getPayload(), ChatMessage.class);
        switch (chatMessage.getType()) {
            case TYPE_REGISTER:
                log.info("inside TYPE_REGISTER");
                userIdSessionMap.put(chatMessage.getId(), session);
                userSet.add(new User(chatMessage.getId(), chatMessage.getName()));
                broadcast(TYPE_PEER_INFO, userSet.toArray());
                break;
            case TYPE_PEER_CHAT:
                log.info("inside TYPE_PEER_CHAT");
                if (userIdSessionMap.containsKey(chatMessage.getTo()) && userIdSessionMap.get(chatMessage.getTo()).isOpen()) {
                    userIdSessionMap.get(chatMessage.getTo()).sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(TYPE_PEER_CHAT, chatMessage))));
                }
                session.sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(TYPE_PEER_CHAT, chatMessage))));
                break;
            case TYPE_PEER_DISCONNECT:
                log.info("inside TYPE_PEER_DISCONNECT");
                userSet = userSet.stream().filter(user -> user.getUid().compareTo(chatMessage.getId()) != 0).collect(Collectors.toSet());
                broadcast(TYPE_PEER_INFO, userSet.toArray());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chatMessage.getType());
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(session.getId() + "  closed");
//        userList = userList.stream().filter(user -> user.getSessionId() != session.getId()).toList();
//        broadcast(TYPE_PEER_INFO, userList.toArray());
    }

    void broadcast(String messageType, Object chatMessage) {
        log.info("in broadcast with " + messageType + "  " + chatMessage);
        try {
            for (User user : userSet) {

                if (userIdSessionMap.containsKey(user.getUid()) && userIdSessionMap.get(user.getUid()).isOpen()) {
                    userIdSessionMap.get(user.getUid()).sendMessage(new TextMessage(objectMapper.writeValueAsBytes(new MessageWrapper(messageType, chatMessage))));

                }
            }
        } catch (IOException e) {
            log.error("The error that we are getting is : ", e.getMessage());
        }

    }
}
