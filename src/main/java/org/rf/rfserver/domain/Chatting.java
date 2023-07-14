package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatting {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;
    @ManyToOne(fetch = LAZY)
    private ChattingRoom chattingRoom;

    private String content;
    private LocalDateTime sendTime;
    private boolean check;
    private String imageFilePath;

}
