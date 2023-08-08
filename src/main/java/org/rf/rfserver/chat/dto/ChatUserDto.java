package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatUserDto {
    private Long speakerId = null;
    private String speakerName = null;
    private String speakerImageUrl = null;
}
