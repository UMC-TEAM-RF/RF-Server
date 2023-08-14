package org.rf.rfserver.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserParty extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JsonManagedReference
    private Party party;

    @ManyToOne(fetch = LAZY)
    @JsonBackReference
    private User user;

    public UserParty(Party party, User user) {
        this.party = party;
        this.user = user;
        this.party.getUsers().add(this);
        this.user.getUserParties().add(this);
    }
}
