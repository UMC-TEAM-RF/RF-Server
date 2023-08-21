package org.rf.rfserver.config.redis;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.service.ChatSubscriber;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperties redisProperties;
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort()); // redisConnectionFactory 를 LettuceConnectionFactory를 이용함
        return lettuceConnectionFactory;
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            MessageListenerAdapter chatListener // 스프링이 빈에 등록된 chatListener 를 자동으로 넣어줌
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer(); // 기본 redisMessageListenerContainer 불러오기
        container.setConnectionFactory(redisConnectionFactory()); // redis 의 connectionFactory 를 지정함
        container.addMessageListener(chatListener, chattingTopic()); // "chat" topic 으로 들어온 메세지를 chatListener 로 보낸다
        return container;
    }
    @Bean
    public MessageListenerAdapter chatListener(ChatSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage"); // MessageListener 를 지정함
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory()); // redis 클라이언트와 redis 서버의 연결을 가져오는 객체
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // redis 의 key 값의 직렬화 역직렬화에 사용하는 Serializer 지정
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDto.class)); // redis 의 value 값의 직렬화 역직렬화에 사용하는 Serializer 지정
        return redisTemplate;
    }
    @Bean
    public ChannelTopic chattingTopic() {
        return new ChannelTopic("chat");
    } // 채팅을 위한 topic 생성
}
