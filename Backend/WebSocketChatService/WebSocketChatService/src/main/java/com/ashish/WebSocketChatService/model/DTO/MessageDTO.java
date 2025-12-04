package com.ashish.WebSocketChatService.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Date;

import com.ashish.WebSocketChatService.model.enums.MessageState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {

   private String conversationId;
   private String messageString;
   private Date timestamp;
   private MessageState state;
   private String senderId;
   private String recieverId;
}
