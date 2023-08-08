package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetChatsReq {
    private Long chatId;
    private Long userId;
}
