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
    private MessageType type = null;
    private ChatUserDto speaker;
    private ScheduleChatDto schedule;
    private Long partyId = null;
    private Long chatId = null;
    private Long replyChatId = null;
    private String content = null;
    private String dateTime = null;
    public ChatDto(Chat chat) {
        DateTimeFormatter chatFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        this.type = chat.getType();
        this.speaker = new ChatUserDto(chat.getSpeaker().getId(), chat.getSpeaker().getNickName(), chat.getSpeaker().getImageUrl());
        this.schedule = type == SCHEDULE ? new ScheduleChatDto(chat.getSchedule()) : null;
        this.partyId = chat.getParty().getId();
        this.chatId = chat.getId();
        this.replyChatId = type == REPLY ? chat.getReplyAtChatId() : null;
        this.content = chat.getContent();
        this.dateTime = chat.getCreatedAt().format(chatFormatter);
    }

    public ChatDto setChatDtoForRes(Long partyId, Long chatId, String speakerName, String speakerImageUrl, LocalDateTime dateTime) {
        DateTimeFormatter chatFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSS");
        this.partyId = partyId;
        this.chatId = chatId;
        this.getSpeaker().setSpeakerName(speakerName);
        this.getSpeaker().setSpeakerImageUrl(speakerImageUrl);
        this.dateTime = dateTime.format(chatFormatter);
        return this;
    }
}
