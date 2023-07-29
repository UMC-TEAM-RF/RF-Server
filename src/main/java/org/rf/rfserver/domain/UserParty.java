package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserParty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    private Party party;
    @ManyToOne(fetch = LAZY)
    private User user;

    public UserParty(Party party, User user) {
        this.party = party;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
        user.getUserParties().add(this);
    }

    public void setParty(Party party) {
        this.party = party;
        party.getUserParties().add(this);
    }
}
