package com.ashish.WebSocketChatService.service;

import java.util.Optional;

import com.ashish.WebSocketChatService.repository.ChatConversationRepository;
import com.ashish.WebSocketChatService.model.ChatConversation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ChatConversationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatConversationService.class);
    
    @Autowired 
    private ChatConversationRepository chatConversationRepository;

    public String createAndGetCoversationId(String senderId, String recieverId) {

        Optional<ChatConversation> conversation;
        try{
            conversation = chatConversationRepository.findBySenderIdAndRecieverId(senderId, recieverId);
        }
        catch(Exception e){
            LOGGER.error("Error while interacting with repository: "+e);
            throw e;
        }

        if(conversation.isPresent()){
            return conversation.get().getConversationId();
        }
        else{
            String conversationId = senderId + "_" + recieverId;

            ChatConversation senderRecieverChat = ChatConversation
                .builder()
                .conversationId(conversationId)
                .senderId(senderId)
                .recieverId(recieverId)
                .build();
            try{
                chatConversationRepository.save(senderRecieverChat);
            }
            catch(Exception e){
                LOGGER.error("Error while saving data senderRecieverChat with repository: "+e);
                throw e;
            }

            ChatConversation recieverSenderChat = ChatConversation
                .builder()
                .conversationId(conversationId)
                .senderId(recieverId)
                .recieverId(senderId)
                .build();
            try{
                chatConversationRepository.save(recieverSenderChat);
            }
            catch(Exception e){
                LOGGER.error("Error while saving data recieverSenderChat with repository: "+e);
                throw e;
            }
            
            return conversationId;
        }

   }

}
