package com.ashish.WebSocketChatService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChatConversation {
    @Id
    private String id;
    private String conversationId;
    private String senderId;
    private String recieverId;
}