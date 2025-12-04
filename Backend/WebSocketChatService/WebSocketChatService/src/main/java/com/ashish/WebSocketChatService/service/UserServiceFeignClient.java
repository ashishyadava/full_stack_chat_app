package com.ashish.WebSocketChatService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

// @FeignClient("chat-app-user-service")
@Service
public class UserServiceFeignClient {

    @RequestMapping("/chat/user/users/{userId}")
    public String findAll(){
        return "";
    };

}
