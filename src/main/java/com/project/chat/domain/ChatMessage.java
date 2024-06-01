package com.project.chat.domain;

import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;
}
