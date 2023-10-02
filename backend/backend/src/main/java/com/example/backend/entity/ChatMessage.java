package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String type;   // "registration" or "chat"
    private String id;     // User ID (for registration)
    private String name;   // Username (for registration)
    private String message;    // Message content (for chat)
    private String from;   // Sender
    private String to;     // Recipient

    // Constructors for user registration message
    public ChatMessage(String type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    // Constructors for chat message
    public ChatMessage(String type, String name, String message, String from, String to) {
        this.type = type;
        this.name = name;
        this.message = message;
        this.from = from;
        this.to = to;
    }
}
