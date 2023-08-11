package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocker_user_id")
    @JsonBackReference
    private User blockerUser; // 차단하는 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_party_id")
    @JsonBackReference
    private Party blockedParty; // 차단 당하는 모임

    public BlockParty(User blockerUser, Party blockedParty) {
        this.blockerUser = blockerUser;
        this.blockedParty = blockedParty;
    }
}