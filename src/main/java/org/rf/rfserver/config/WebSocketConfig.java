package org.rf.rfserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub", "/topic"); // 스프링에서 사용하는 내장 브로커 사용 설정, 메시지 핸들러로 라우팅 되는 prefix
        registry.setApplicationDestinationPrefixes("/pub"); // @MessageMapping 어노테이션이 붙은 메시지 핸들러를 거쳐 브로커로 이동하는 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpointRegistry) {
        endpointRegistry.addEndpoint("/ws") // /ws 경로로 들어오는 WebSocket을 설정 (endPoint)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
