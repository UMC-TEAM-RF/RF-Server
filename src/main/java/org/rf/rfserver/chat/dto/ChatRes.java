package org.rf.rfserver.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rf.rfserver.constant.MessageType;

@Getter
@Setter
@NoArgsConstructor
public class ChatRes {
    private Long chatId;
    private Long userId;
    private Long partyId;
    private String content;
    private MessageType type;
    public ChatRes(Long chatId, ChatReq chatReq) {
        this.chatId = chatId;
        this.userId = chatReq.getUserId();
        this.partyId = chatReq.getPartyId();
        this.content = chatReq.getContent();
        this.type = chatReq.getType();
    }
}
