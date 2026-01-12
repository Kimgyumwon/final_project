package com.cafe.erp.security.websocket;


import org.springframework.messaging.*;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class StompAuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            Authentication authentication =
                (Authentication) accessor.getUser();

            if (authentication == null) {
                throw new IllegalStateException("인증되지 않은 WebSocket 접근");
            }

        }

        return message;
    }
}
