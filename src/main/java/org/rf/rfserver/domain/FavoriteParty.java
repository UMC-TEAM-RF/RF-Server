package org.rf.rfserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteParty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Party party;

    public FavoriteParty(User user, Party party) {
        this.user = user;
        this.party = party;
        user.getFavoriteParties().add(this);
    }

    public void deleteFavoriteParty() {
        user.getFavoriteParties().remove(this);
    }
}