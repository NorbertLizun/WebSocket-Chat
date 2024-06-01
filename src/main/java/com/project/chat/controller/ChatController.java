package com.project.chat.controller;

import com.project.chat.domain.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import static java.util.Objects.requireNonNull;

@Controller
public class ChatController {

    @SendTo("/topic/public")
    @MessageMapping("/chat.sendMessage")
    public ChatMessage sendMessage(final @Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @SendTo("/topic/public")
    @MessageMapping("/chat.addUser")
    public ChatMessage addUser(final @Payload ChatMessage chatMessage,
                               final SimpMessageHeaderAccessor accessor) {
        // Add user in WebSocket session
        requireNonNull(accessor.getSessionAttributes()).put("username", chatMessage.getSender());
        return chatMessage;
    }
}