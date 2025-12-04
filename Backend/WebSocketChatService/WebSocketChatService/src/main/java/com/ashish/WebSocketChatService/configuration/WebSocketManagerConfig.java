package com.ashish.WebSocketChatService.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import org.springframework.messaging.converter.MessageConverter;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketManagerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        
        config.setApplicationDestinationPrefixes("/chat_app");
        
        config.enableSimpleBroker( "/user_messages_topic");
        
        config.setUserDestinationPrefix("/user_messages_topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        
        registry.addEndpoint("/web_socket").setAllowedOriginPatterns("*").withSockJS();
    }

    private MessageConverter createJsonMessageConverter() {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MediaType.APPLICATION_JSON);
        converter.setContentTypeResolver(resolver);
        
        return converter;
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> converters) {

        converters.add(createJsonMessageConverter());

        return false;
    }
}

