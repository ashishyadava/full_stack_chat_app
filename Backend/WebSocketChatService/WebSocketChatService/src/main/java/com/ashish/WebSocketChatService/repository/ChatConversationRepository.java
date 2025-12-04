package com.ashish.WebSocketChatService.repository;

import com.ashish.WebSocketChatService.model.ChatConversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatConversationRepository extends MongoRepository<ChatConversation, String> {

    Optional<ChatConversation> findBySenderIdAndRecieverId(String senderId, String recieverId);
}
