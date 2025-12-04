package com.ashish.WebSocketChatService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// @EnableFeignClients
public class WebSocketChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketChatServiceApplication.class, args);
	}

}
