package com.ashish.WebSocketChatService.repository;

import com.ashish.WebSocketChatService.model.Message;
import com.ashish.WebSocketChatService.model.enums.MessageState;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository
        extends MongoRepository<Message, String> {

    long countBySenderIdAndRecieverIdAndState(
            String senderId, String recipientId, MessageState state);

    List<Message> findByConversationId(String conversationId);
}
