package org.rf.rfserver.chat.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatReq;
import org.rf.rfserver.chat.service.ChatPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatPublisher chatPublisher;
    @MessageMapping("/redisChat")
    public void sendMessage(@Payload ChatReq chatReq) {
        chatPublisher.sendMessage(chatReq);}
}
