package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Block {
    @Id
    @Column(name = "block_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blocker_id")
    private User blocker; // 차단하는 user

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blocked_id") // 차단 당하는 user
    private User blocked;

    @Builder
    public Block(User blocker, User blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
    }


}
