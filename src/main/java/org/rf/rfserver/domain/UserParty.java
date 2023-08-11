package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserParty extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    private Party party;
    @ManyToOne(fetch = LAZY)
    private User user;

    public UserParty(Party party, User user) {
        this.party = party;
        this.user = user;
        this.party.getUserParties().add(this);
        this.user.getUserParties().add(this);
    }
}
