package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Member;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteChatting {
    @Id @GeneratedValue
    @Column(name = "favorite_chatting_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = LAZY)
    private User user;
}
