package com.cafe.erp.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.cafe.erp.security.websocket.StompAuthChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	 @Autowired
	 private StompAuthChannelInterceptor stompAuthChannelInterceptor;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		   registry.addEndpoint("/ws")
           .setAllowedOriginPatterns("*")
           .withSockJS();
	}
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	    registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
        registry.setUserDestinationPrefix("/user");
	}
	
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(stompAuthChannelInterceptor);
	}
}
