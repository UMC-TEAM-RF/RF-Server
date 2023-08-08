package org.rf.rfserver.chat.controller;

import lombok.RequiredArgsConstructor;
import org.rf.rfserver.chat.dto.ChatDto;
import org.rf.rfserver.chat.dto.GetChatsReq;
import org.rf.rfserver.chat.service.ChatService;
import org.rf.rfserver.config.BaseException;
import org.rf.rfserver.config.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping()
    public BaseResponse<List<ChatDto>> getChats(@RequestBody GetChatsReq getChatsReq) {
        try {
            return new BaseResponse<>(chatService.getChats(getChatsReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
