package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetChatsReq {
    private Long chatId;
    private Long userId;
}
