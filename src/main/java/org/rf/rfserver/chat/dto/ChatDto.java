package org.rf.rfserver.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rf.rfserver.constant.MessageType;
import org.rf.rfserver.domain.Chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.rf.rfserver.constant.MessageType.REPLY;
import static org.rf.rfserver.constant.MessageType.SCHEDULE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    /**
     * 프론트 측에서 송수신을 같은 형식으로 요구해 생성한 DTO
     */
    private MessageType type = null;
    private ChatUserDto speaker;
    private ChatUserDto victim;
    private ScheduleChatDto schedule;
    private Long partyId = null;
    private String partyName;
    private Long chatId = null;
    private Long replyChatId = null;
    private String content = null;
    private String langCode; // 파파고 번역에 사용하는 언어코드
    private String dateTime = null;
    public ChatDto(Chat chat) {
        DateTimeFormatter chatFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        this.type = chat.getType();
        this.speaker = new ChatUserDto(chat.getSpeakerUserId(), chat.getSpeakerUserName(), chat.getSpeakerUserImageUrl());
        this.schedule = type == SCHEDULE ? new ScheduleChatDto(chat.getSchedule()) : null;
        this.partyId = chat.getPartyId();
        this.partyName = chat.getPartyName();
        this.chatId = chat.getId();
        this.replyChatId = type == REPLY ? chat.getReplyAtChatId() : null;
        this.content = chat.getContent();
        this.dateTime = chat.getCreatedAt().format(chatFormatter);
    }

    public ChatDto setChatDtoForRes(Long chatId, LocalDateTime dateTime) {
        DateTimeFormatter chatFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        this.chatId = chatId;
        this.dateTime = dateTime.format(chatFormatter);
        return this;
    }
}
