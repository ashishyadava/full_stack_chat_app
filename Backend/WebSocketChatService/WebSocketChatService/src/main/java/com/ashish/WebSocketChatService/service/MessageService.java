package com.ashish.WebSocketChatService.service;

import java.util.List;

import com.ashish.WebSocketChatService.repository.MessageRepository;
import com.ashish.WebSocketChatService.model.Message;
import com.ashish.WebSocketChatService.model.enums.MessageState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ashish.WebSocketChatService.model.DTO.MessageDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);
    
    @Autowired 
    private MessageRepository messageRepository;

    @Autowired 
    private ChatConversationService chatConversationService;

    @Autowired 
    private MongoOperations mongoOperations;

    private Message messageDtoToMessage(MessageDTO messageDTO){

        String coversationId = chatConversationService.createAndGetCoversationId(messageDTO.getSenderId(), messageDTO.getRecieverId());

        messageDTO.setConversationId(coversationId);

        Message message = Message
                .builder()
                .conversationId(coversationId)
                .messageString(messageDTO.getMessageString())
                .timestamp(messageDTO.getTimestamp())
                .senderId(messageDTO.getSenderId())
                .recieverId(messageDTO.getRecieverId())
                .build();

        return message;

    }

    //Create
    public Message sendMessage(MessageDTO messageDTO) {

        Message message = messageDtoToMessage(messageDTO);

        message.setState(MessageState.SENT_UNREAD);
        messageRepository.save(message);
        return message;
    }

    //Read
    public long countUnreadMessages(String senderId, String recieverId) {

        long unreadMessages = messageRepository.countBySenderIdAndRecieverIdAndState(senderId, recieverId, MessageState.SENT_UNREAD);

        return unreadMessages;
    }

    //Update
    public void markRead(String senderId, String recieverId) {
        try{
            Criteria quetyCriteria = Criteria.where("recieverId").is(recieverId).and("senderId").is(senderId);
            Query query = new Query(quetyCriteria);

            Update update = Update.update("state", MessageState.READ);
            mongoOperations.updateMulti(query, update, Message.class);
        }
        catch(Exception e){
            LOGGER.error("Error while marking conversation as read: "+e);
            throw e;
        }
    }

    //Read
    public List<Message> findAllConversationMessages(String senderId, String recieverId) {

        String coversationId = chatConversationService.createAndGetCoversationId(senderId, recieverId);
        
        markRead(senderId, recieverId);
        
        List<Message> messages; 
        try{
            messages = messageRepository.findByConversationId(coversationId);
        }
        catch(Exception e){
            LOGGER.error("Error while finding messages for conversation: "+e);
            throw e;
        }

        return messages;
    }

}
