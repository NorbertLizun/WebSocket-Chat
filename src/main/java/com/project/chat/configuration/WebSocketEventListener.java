package com.project.chat.configuration;

import com.project.chat.domain.ChatMessage;
import com.project.chat.domain.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent disconnectEvent) {
        final StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        final String username = (String) Objects.requireNonNull(stompHeaderAccessor.getSessionAttributes()).get("username");

        log.info("User {} disconnected", username);

        final ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.LEAVER)
                .sender(username)
                .build();

        messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
}