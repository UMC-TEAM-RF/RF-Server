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
        registry.enableSimpleBroker("/listen"); // 스프링에서 사용하는 내장 브로커 사용 설정, 메시지 핸들러로 라우팅 되는 prefix
        registry.setApplicationDestinationPrefixes("/speak"); // 해당 prefix가 붙으면 @MessageMapping 어노테이션이 붙은 메시지 핸들러로 향함
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpointRegistry) {
        endpointRegistry.addEndpoint("/ws") // 등록을 위한 endPoint 주소 지정
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
