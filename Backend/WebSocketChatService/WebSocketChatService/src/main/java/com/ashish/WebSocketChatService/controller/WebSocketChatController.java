package com.ashish.WebSocketChatService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.ashish.WebSocketChatService.service.MessageService;
import com.ashish.WebSocketChatService.service.UserServiceFeignClient;

import java.util.List;

import com.ashish.WebSocketChatService.model.Message;
import com.ashish.WebSocketChatService.model.DTO.MessageDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class WebSocketChatController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChatController.class);

    @Autowired 
    private MessageService messageService;

    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired 
    UserServiceFeignClient userServiceFeignClient;

    @MessageMapping("/chatApp/send")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        
        try{
            messageService.sendMessage(messageDTO);

            simpMessagingTemplate.convertAndSendToUser(
                messageDTO.getRecieverId(),
                "/chatMessageQueue",
                messageDTO);
        }
        catch(Exception e){
            LOGGER.error("Error in controller sendMessage is: "+e);
        }

    }

    @GetMapping("/chatApp/{senderId}/{recieverId}/count")
    public ResponseEntity<?> countUnreadMessages(
            @PathVariable String senderId,
            @PathVariable String recieverId) {
        
        Long unreadMessagesCount = 0L;
        try{
            unreadMessagesCount = messageService.countUnreadMessages(senderId, recieverId);
        }
        catch(Exception e){
            LOGGER.error("Error in controller countUnreadMessages is: "+e);
            return ResponseEntity.badRequest().body("Error in controller countUnreadMessages is: "+e);
        }

        return ResponseEntity.ok(unreadMessagesCount);
    }

    @GetMapping("/chatApp/{senderId}/{recieverId}/getMessages")
    public ResponseEntity<?> findChatMessages ( 
        @PathVariable String senderId,
        @PathVariable String recieverId) {
        
            List<Message> messages = null;
            try{
                messages = messageService.findAllConversationMessages(senderId, recieverId);
            }
            catch(Exception e){
                LOGGER.error("Error in controller findChatMessages is: "+e);
                return ResponseEntity.badRequest().body("Error in controller findChatMessages is: "+e);
            }
            return ResponseEntity.ok(messages);
    }

    @GetMapping("/getUsers/{userId}")
    public ResponseEntity<?> findAll(@PathVariable String userId) {

        userServiceFeignClient.findAll();
        return ResponseEntity.ok("messages");
    }

}
