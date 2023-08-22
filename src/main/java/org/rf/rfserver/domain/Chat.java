package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.rf.rfserver.constant.MessageType;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageType type;
//    private boolean checked;

    private Long speakerUserId;
    private String speakerUserName;
    private String speakerUserImageUrl;

    private Long victimUserId;
    private String victimUserName;
    private String victimUserImageUrl;

    private Long partyId;
    private String partyName;

    private Long replyAtChatId;
    @ManyToOne(fetch = LAZY)
    private Schedule schedule;
    public Chat(String content, MessageType type, Long speakerUserId, Long partyId) {
        this.content = content;
        this.type = type;
        this.speakerUserId = speakerUserId;
        this.partyId = partyId;
    }
    public Chat setReplyAtChatId(Long replyAtChatId) {
        this.replyAtChatId = replyAtChatId;
        return this;
    }
    public Chat setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }
    public Chat setVictim(Long victimUserId) {
        this.victimUserId = victimUserId;
        return this;
    }
}