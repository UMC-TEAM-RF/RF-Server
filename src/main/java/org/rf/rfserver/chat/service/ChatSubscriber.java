package org.rf.rfserver.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import static org.rf.rfserver.constant.MessageType.ERROR_MASSAGE;

@RequiredArgsConstructor
@Service
public class ChatSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        ChatDto chatDto = new ChatDto();
        try {
            chatDto = objectMapper.readValue(publishMessage, ChatDto.class);
            messagingTemplate.convertAndSend("/listen/chat/"+chatDto.getPartyId(), chatDto);
        } catch(Exception e) {
            chatDto.setType(ERROR_MASSAGE);
            messagingTemplate.convertAndSend("/listen/chat/"+chatDto.getPartyId(), chatDto);
        }
    }
}
