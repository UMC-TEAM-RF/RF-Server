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

    @ManyToOne(fetch = LAZY)
    private User speaker;

    @ManyToOne(fetch = LAZY)
    private User victim;

    @ManyToOne(fetch = LAZY)
    private Party party;

    private Long replyAtChatId;
    @ManyToOne(fetch = LAZY)
    private Schedule schedule;
    public Chat(String content, MessageType type, User speaker, Party party) {
        this.content = content;
        this.type = type;
        this.speaker = speaker;
        this.party = party;
    }
    public Chat setReplyAtChatId(Long replyAtChatId) {
        this.replyAtChatId = replyAtChatId;
        return this;
    }
    public Chat setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }
    public Chat setVictim(User victim) {
        this.victim = victim;
        return this;
    }
}