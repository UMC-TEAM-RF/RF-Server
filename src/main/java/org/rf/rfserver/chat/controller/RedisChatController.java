package org.rf.rfserver.chat.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.service.ChatPublisher;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class RedisChatController {
    private final ChatPublisher chatPublisher;
    @MessageMapping("/chat/{partyId}")
    public void sendMessage(@Payload ChatDto chatDto, @DestinationVariable Long partyId) {
        chatPublisher.sendMessage(chatDto, partyId);
    }
}
