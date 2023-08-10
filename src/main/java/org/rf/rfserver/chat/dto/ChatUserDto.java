package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatUserDto {
    private Long userId = null;
    private String userName = null;
    private String userImageUrl = null;
}
