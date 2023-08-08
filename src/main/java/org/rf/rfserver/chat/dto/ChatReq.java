package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rf.rfserver.constant.MessageType;

@Getter
@Setter
@AllArgsConstructor
public class ChatReq {
    private Long userId;
    private Long partyId;
    private String content;
    private MessageType type;
}
