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

    /**
     * 클라이언트가 "/speak/chat/{partyId}" 주소로 송신한 소켓을 수신
     * Payload : 소켓 내의 정보
     * DestinationVariable : PathVariable과 같은 역할
     * @param chatDto
     * @param partyId
     */
    @MessageMapping("/chat/{partyId}")
    public void sendMessage(@Payload ChatDto chatDto, @DestinationVariable Long partyId) {
        chatPublisher.sendMessage(chatDto, partyId);
    }
}
